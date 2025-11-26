INSERT INTO users (id, username, address, house_no, post, city, email, password_hash)
VALUES
(1, 'John Doe', '12 Main St', '1A', 'Central', 'Gotham', 'john@example.com', '$2a$10$pQlXa16aJZkWRfeWh3t7..SbqM7VV.t/Ikpn2kEKSREf2LqlrQ1kG'),
(2, 'Jane Smith', '76 High St', '12B', 'West',   'Metropolis', 'jane@example.com', '$2a$10$pQlXa16aJZkWRfeWh3t7..SbqM7VV.t/Ikpn2kEKSREf2LqlrQ1kG'),
(3, 'Ravi Kumar', '44 Park Ln', '22C', 'East',   'StarCity',    'ravi@example.com', '$2a$10$pQlXa16aJZkWRfeWh3t7..SbqM7VV.t/Ikpn2kEKSREf2LqlrQ1kG');

-- admin table
INSERT INTO admin_users (id, email, password_hash)
VALUES (1, 'admin@fix-and-ride.com', '$2a$10$pQlXa16aJZkWRfeWh3t7..SbqM7VV.t/Ikpn2kEKSREf2LqlrQ1kG');

-- products/services
INSERT INTO services (id, name, description, price)
VALUES
(1, 'AC Repair', 'Complete AC servicing', 29.99),
(2, 'TV Installation', 'TV wall mount installation', 19.99),
(3, 'Plumbing', 'Leak fixing and pipe installation', 14.50);




INSERT INTO services (key_name, name, description) VALUES
('car-labor', 'Car + Labor', 'I drive my own car and help you move small stuff or transport you.'),
('labor-transporter', 'Labor + Transporter', 'I drive a hired transporter. You pay the transporter separately.'),
('labor-only', 'Labor Only (Moving)', 'Heavy lifting, loading/unloading, stairs — I do it all!'),
('repairs', 'Repairs & Installations', 'Kitchen, furniture, closets, plumbing — I bring the tools, you get the fix!'),
('tool-lending', 'Tool Lending', 'Need tools but not the labor? Rent my professional-grade tools for your own DIY projects.'),
('taxi', 'Taxi Service', 'Emergency pickup or drop-off — I can get you there quickly and safely.');
