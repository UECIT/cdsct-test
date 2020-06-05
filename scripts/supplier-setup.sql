INSERT INTO cdss_decoupling.cdss_supplier (base_url, name)
VALUES ('http://cdss:8080/fhir', 'Dockerized CDSS');

INSERT IGNORE INTO
    cdss_decoupling.users (username, name, password, enabled, role, supplierId)
VALUES
('admin', 'Admin User', '\$2a\$10\$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu', true, 'ROLE_ADMIN', 'adminSupplier'), # admin@123
('supplier1', 'Supplier 1 Admin', '\$2a\$10\$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu', true, 'ROLE_SUPPLIER_ADMIN', 'supplier1'), # admin@123
('supplier2', 'Supplier 2 Admin', '\$2a\$10\$hbxecwitQQ.dDT4JOFzQAulNySFwEpaFLw38jda6Td.Y/cOiRzDFu', true, 'ROLE_SUPPLIER_ADMIN', 'supplier2') # admin@123
;