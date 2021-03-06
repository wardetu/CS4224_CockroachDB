USE wholesaledb;

-- O_C_ID only will do because O_W_ID and O_D_ID are part of the primary index.
DROP INDEX IF EXISTS wholesale.orders@order_by_customer CASCADE;
CREATE INDEX order_by_customer ON wholesale.orders (O_C_ID) STORING (O_ENTRY_D, O_CARRIER_ID);

DROP INDEX IF EXISTS wholesale.order_line@order_by_item CASCADE;
CREATE INDEX order_by_item ON wholesale.order_line (OL_I_ID);

DROP INDEX IF EXISTS wholesale.customer@customer_balance CASCADE;
CREATE INDEX customer_balance ON wholesale.customer (C_BALANCE DESC) STORING (C_FIRST, C_MIDDLE, C_LAST);
