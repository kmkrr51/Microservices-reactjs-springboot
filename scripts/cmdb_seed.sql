INSERT INTO configuration_items (id, name, type, description, status, owner, location, cost_center, business_service, created_at, updated_at, created_by, updated_by, version) VALUES
('a1b2c3d4-0001-0001-0001-000000000001','WEB-SERVER-01','Server','Primary web application server','ACTIVE','IT Operations','DC-East-Rack-A1','CC-IT-001','Customer Portal',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0002-0002-0002-000000000002','DB-SERVER-01','Server','Primary PostgreSQL database server','ACTIVE','DBA Team','DC-East-Rack-B2','CC-IT-001','Data Management',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0003-0003-0003-000000000003','NETWORK-SWITCH-01','Network','Core network switch floor 1','ACTIVE','Network Team','DC-East-Rack-C1','CC-IT-002','Network Infrastructure',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0004-0004-0004-000000000004','FIREWALL-01','Network','Perimeter firewall - primary','ACTIVE','Security Team','DC-East-Rack-C2','CC-SEC-001','Security',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0005-0005-0005-000000000005','STORAGE-SAN-01','Storage','SAN storage array - primary','ACTIVE','IT Operations','DC-East-Rack-D1','CC-IT-001','Data Management',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0006-0006-0006-000000000006','APP-SERVER-02','Server','Secondary application server for failover','ACTIVE','IT Operations','DC-West-Rack-A1','CC-IT-001','Customer Portal',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0007-0007-0007-000000000007','LOAD-BALANCER-01','Network','HTTP load balancer for web tier','ACTIVE','Network Team','DC-East-Rack-C3','CC-IT-002','Customer Portal',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0008-0008-0008-000000000008','BACKUP-SERVER-01','Server','Backup and recovery server','ACTIVE','IT Operations','DC-West-Rack-B1','CC-IT-003','Business Continuity',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0009-0009-0009-000000000009','MONITOR-SERVER-01','Server','Monitoring and observability platform','ACTIVE','IT Operations','DC-East-Rack-A2','CC-IT-001','IT Operations',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0010-0010-0010-000000000010','OLD-SERVER-01','Server','Legacy application server pending decommission','RETIRED','IT Operations','DC-East-Rack-E1','CC-IT-001','Legacy Systems',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0011-0011-0011-000000000011','VPN-GATEWAY-01','Network','VPN gateway for remote access','ACTIVE','Security Team','DC-East-Rack-C4','CC-SEC-001','Remote Access',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0012-0012-0012-000000000012','KAFKA-SERVER-01','Middleware','Apache Kafka message broker cluster','ACTIVE','Platform Team','DC-East-Rack-F1','CC-IT-004','Integration Platform',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0013-0013-0013-000000000013','DEV-SERVER-01','Server','Development environment server','PENDING','Dev Team','DC-East-Rack-G1','CC-DEV-001','Development',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0014-0014-0014-000000000014','PROXY-SERVER-01','Server','Reverse proxy and API gateway host','ACTIVE','Platform Team','DC-East-Rack-A3','CC-IT-002','Customer Portal',NOW(),NOW(),'system','system',1),
('a1b2c3d4-0015-0015-0015-000000000015','DECOM-SWITCH-01','Network','Decommissioned legacy network switch','ARCHIVED','Network Team','DC-East-Rack-Z1','CC-IT-002','Network Infrastructure',NOW(),NOW(),'system','system',1);
SELECT COUNT(*) AS total_rows FROM configuration_items;
