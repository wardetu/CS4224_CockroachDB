USE wholesaledb;

IMPORT INTO wholesale.warehouse (W_ID, W_NAME, W_STREET_1, W_STREET_2, W_CITY, W_STATE, W_ZIP, W_TAX, W_YTD) CSV DATA ('nodelocal://4/warehouse.csv') WITH nullif = 'null';

IMPORT INTO wholesale.district (D_W_ID, D_ID, D_NAME, D_STREET_1, D_STREET_2, D_CITY, D_STATE, D_ZIP, D_TAX, D_YTD, D_NEXT_O_ID) CSV DATA ('nodelocal://4/district.csv') WITH nullif = 'null';

IMPORT INTO wholesale.customer (C_W_ID, C_D_ID, C_ID, C_FIRST, C_MIDDLE, C_LAST, C_STREET_1, C_STREET_2, C_CITY, C_STATE, C_ZIP, C_PHONE, C_SINCE, C_CREDIT, C_CREDIT_LIM, C_DISCOUNT, C_BALANCE, C_YTD_PAYMENT, C_PAYMENT_CNT, C_DELIVERY_CNT, C_DATA) CSV DATA ('nodelocal://4/customer.csv') WITH nullif = 'null';

IMPORT INTO wholesale.orders (O_W_ID, O_D_ID, O_ID, O_C_ID, O_CARRIER_ID, O_OL_CNT, O_ALL_LOCAL, O_ENTRY_D) CSV DATA ('nodelocal://4/order.csv') WITH nullif = 'null';

IMPORT INTO wholesale.item (I_ID, I_NAME, I_PRICE, I_IM_ID, I_DATA) CSV DATA ('nodelocal://4/item.csv') WITH nullif = 'null';

IMPORT INTO wholesale.order_line (OL_W_ID, OL_D_ID, OL_O_ID, OL_NUMBER, OL_I_ID, OL_DELIVERY_D, OL_AMOUNT, OL_SUPPLY_W_ID, OL_QUANTITY, OL_DIST_INFO) CSV DATA ('nodelocal://4/order-line.csv') WITH nullif = 'null';

IMPORT INTO wholesale.stock (S_W_ID, S_I_ID, S_QUANTITY, S_YTD, S_ORDER_CNT, S_REMOTE_CNT, S_DIST_01, S_DIST_02, S_DIST_03, S_DIST_04, S_DIST_05, S_DIST_06, S_DIST_07, S_DIST_08, S_DIST_09, S_DIST_10, S_DATA) CSV DATA ('nodelocal://4/stock.csv') WITH nullif = 'null';

IMPORT INTO wholesale.order_by_item (I_ID, O_W_ID, O_D_ID, O_ID) CSV DATA ('nodelocal://4/order-by-item.csv') WITH nullif = 'null';
