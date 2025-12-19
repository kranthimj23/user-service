-- Sample Data for User Service
-- Version: 1.0.0
-- Description: Insert sample users for testing

-- Insert sample admin user
INSERT INTO users (id, auth_id, email, first_name, last_name, phone_number, date_of_birth, status, kyc_status, account_number, currency)
VALUES (
    'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
    'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
    'admin@mobilebanking.com',
    'Admin',
    'User',
    '+14155551000',
    '1985-01-15',
    'ACTIVE',
    'VERIFIED',
    '1000000001',
    'USD'
);

-- Insert sample regular user
INSERT INTO users (id, auth_id, email, first_name, last_name, phone_number, date_of_birth, status, kyc_status, account_number, currency)
VALUES (
    'e1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
    'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
    'john.doe@example.com',
    'John',
    'Doe',
    '+14155551234',
    '1990-06-20',
    'ACTIVE',
    'VERIFIED',
    '1000000002',
    'USD'
);

-- Insert sample test user
INSERT INTO users (id, auth_id, email, first_name, last_name, phone_number, date_of_birth, status, kyc_status, account_number, currency)
VALUES (
    'f2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
    'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33',
    'jane.smith@example.com',
    'Jane',
    'Smith',
    '+14155555678',
    '1988-03-10',
    'PENDING_VERIFICATION',
    'PENDING',
    '1000000003',
    'USD'
);

-- Insert sample profiles
INSERT INTO user_profiles (id, user_id, address_line1, city, state, postal_code, country, occupation, employer, preferred_language, timezone, notifications_enabled, marketing_enabled)
VALUES (
    'a1eebc99-9c0b-4ef8-bb6d-6bb9bd380a44',
    'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11',
    '100 Admin Street',
    'San Francisco',
    'California',
    '94102',
    'United States',
    'System Administrator',
    'Mobile Banking Inc.',
    'en',
    'America/Los_Angeles',
    true,
    false
);

INSERT INTO user_profiles (id, user_id, address_line1, city, state, postal_code, country, occupation, employer, preferred_language, timezone, notifications_enabled, marketing_enabled)
VALUES (
    'b2eebc99-9c0b-4ef8-bb6d-6bb9bd380a55',
    'e1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22',
    '123 Main Street',
    'New York',
    'New York',
    '10001',
    'United States',
    'Software Engineer',
    'Tech Corp Inc.',
    'en',
    'America/New_York',
    true,
    true
);
