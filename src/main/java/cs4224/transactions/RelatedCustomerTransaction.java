package cs4224.transactions;

import cs4224.dao.OrderByItemDao;
import cs4224.dao.OrderDao;
import cs4224.dao.OrderLineDao;
import cs4224.entities.Customer;
import cs4224.entities.Order;

import java.util.HashSet;
import java.util.List;

public class RelatedCustomerTransaction extends BaseTransaction {
    private final OrderDao orderDao;
    private final OrderLineDao orderLineDao;
    private final OrderByItemDao orderByItemDao;

    public RelatedCustomerTransaction(OrderDao orderDao, OrderLineDao orderLineDao, OrderByItemDao orderByItemDao) {
        this.orderDao = orderDao;
        this.orderLineDao = orderLineDao;
        this.orderByItemDao = orderByItemDao;
    }

    @Override
    public void execute(String[] dataLines, String[] parameters) throws Exception {
        final long customerWarehouseId = Long.parseLong(parameters[1]);
        final long customerDistrictId = Long.parseLong(parameters[2]);
        final long customerId = Long.parseLong(parameters[3]);

        HashSet<Customer> relatedCustomers = executeAndGetResult(customerWarehouseId, customerDistrictId, customerId);
        System.out.printf("Number of relatedCustomers: %d\n", relatedCustomers.size());
        System.out.printf("Related customers (C_W_ID, C_D_ID, C_ID):");
        int count = 1;
        for (Customer customer : relatedCustomers) {
            if (count == relatedCustomers.size()) {
                System.out.printf(" %s", customer.toSpecifier());
            } else {
                System.out.printf(" %s,", customer.toSpecifier());
            }
            count++;
        }
        System.out.printf("\n");
    }

    public HashSet<Customer> executeAndGetResult(Long customerWarehouseId, Long customerDistrictId, Long customerId)
            throws Exception {
        List<Long> orderIds = orderDao.getOrderIdsOfCustomer(customerWarehouseId, customerDistrictId, customerId);
        HashSet<Order> relatedOrders = new HashSet<>();
        for (Long orderId : orderIds) {
            HashSet<Order> result = getRelatedOrders(Order.builder()
                    .warehouseId(customerWarehouseId)
                    .districtId(customerDistrictId)
                    .id(orderId)
                    .build());
            relatedOrders.addAll(result);
        }
        return getCustomersOfOrders(relatedOrders);
    }

    public HashSet<Order> getRelatedOrders(Order order) throws Exception {
        HashSet<Long> itemIdsSet = orderLineDao.getItemIdsOfOrder(order.getWarehouseId(), order.getDistrictId(),
                order.getId());
        HashSet<Order> result = new HashSet<>();

        for (Long itemId : itemIdsSet) {
            List<Order> relatedOrders = orderByItemDao.getOrdersOfItem(itemId);

            for (Order relatedOrder : relatedOrders) {
                if (relatedOrder.getWarehouseId() == order.getWarehouseId()) {
                    continue;
                }

                HashSet<Long> relatedOrderItemIds = orderLineDao.getItemIdsOfOrder(relatedOrder.getWarehouseId(),
                        relatedOrder.getDistrictId(), relatedOrder.getId());
                for (Long relatedOrderItemId : relatedOrderItemIds) {
                    if (relatedOrderItemId != itemId && itemIdsSet.contains(relatedOrderItemId)) {
                        result.add(relatedOrder);
                        break;
                    }
                }
            }
        }

        return result;
    }

    public HashSet<Customer> getCustomersOfOrders(HashSet<Order> orders) throws Exception {
        HashSet<Customer> result = new HashSet<>();
        for (Order order : orders) {
            Long customerId = orderDao.getCustomerIdOfOrder(order.getWarehouseId(), order.getDistrictId(),
                    order.getId());
            result.add(Customer.builder()
                    .warehouseId(order.getWarehouseId())
                    .districtId(order.getDistrictId())
                    .id(customerId)
                    .build());
        }
        return result;
    }
}
