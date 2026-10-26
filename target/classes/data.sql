-- Seed Initial Data for BookMyShow

-- 1. Cities
INSERT IGNORE INTO cities (id, name, state) VALUES (1, 'Mumbai', 'Maharashtra');
INSERT IGNORE INTO cities (id, name, state) VALUES (2, 'Delhi', 'Delhi');
INSERT IGNORE INTO cities (id, name, state) VALUES (3, 'Bengaluru', 'Karnataka');

-- 2. Theatres
INSERT IGNORE INTO theatres (id, name, address, city_id) VALUES (1, 'PVR Phoenix Mills', 'Lower Parel, Mumbai', 1);
INSERT IGNORE INTO theatres (id, name, address, city_id) VALUES (2, 'INOX Megaplex Inorbit', 'Malad West, Mumbai', 1);
INSERT IGNORE INTO theatres (id, name, address, city_id) VALUES (3, 'PVR Select Citywalk', 'Saket, New Delhi', 2);

-- 3. Screens
INSERT IGNORE INTO screens (id, name, total_seats, theatre_id) VALUES (1, 'Audi 1 (IMAX)', 15, 1);
INSERT IGNORE INTO screens (id, name, total_seats, theatre_id) VALUES (2, 'Audi 2 (4DX)', 15, 1);

-- 4. Seats for Screen 1
-- Row A (SILVER)
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (1, 'A1', 'A', 1, 'SILVER', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (2, 'A2', 'A', 2, 'SILVER', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (3, 'A3', 'A', 3, 'SILVER', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (4, 'A4', 'A', 4, 'SILVER', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (5, 'A5', 'A', 5, 'SILVER', 1);

-- Row B (GOLD)
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (6, 'B1', 'B', 1, 'GOLD', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (7, 'B2', 'B', 2, 'GOLD', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (8, 'B3', 'B', 3, 'GOLD', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (9, 'B4', 'B', 4, 'GOLD', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (10, 'B5', 'B', 5, 'GOLD', 1);

-- Row C (PLATINUM)
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (11, 'C1', 'C', 1, 'PLATINUM', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (12, 'C2', 'C', 2, 'PLATINUM', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (13, 'C3', 'C', 3, 'PLATINUM', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (14, 'C4', 'C', 4, 'PLATINUM', 1);
INSERT IGNORE INTO seats (id, seat_number, seat_row, seat_col, seat_type, screen_id) VALUES (15, 'C5', 'C', 5, 'PLATINUM', 1);

-- 5. Movies
INSERT IGNORE INTO movies (id, title, genre, language, duration_minutes, rating, release_date, description)
VALUES (1, 'Inception', 'Sci-Fi / Thriller', 'English', 148, 8.8, '2010-07-16', 'A thief who steals corporate secrets through the use of dream-sharing technology.');

INSERT IGNORE INTO movies (id, title, genre, language, duration_minutes, rating, release_date, description)
VALUES (2, 'Interstellar', 'Sci-Fi / Adventure', 'English', 169, 8.7, '2014-11-07', 'When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot is tasked to pilot a spacecraft.');

INSERT IGNORE INTO movies (id, title, genre, language, duration_minutes, rating, release_date, description)
VALUES (3, 'Oppenheimer', 'Biography / Drama', 'English', 180, 8.9, '2023-07-21', 'The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.');

-- 6. Shows
INSERT IGNORE INTO shows (id, movie_id, screen_id, show_date, start_time, end_time, ticket_price)
VALUES (1, 1, 1, '2026-10-30', '18:00:00', '20:30:00', 300.00);

INSERT IGNORE INTO shows (id, movie_id, screen_id, show_date, start_time, end_time, ticket_price)
VALUES (2, 2, 1, '2026-10-30', '21:00:00', '23:50:00', 350.00);

-- 7. Users
INSERT IGNORE INTO users (id, name, email, password, phone_number, created_at)
VALUES (1, 'Yash Choudhari', 'yash@example.com', 'secret123', '+919876543210', NOW());
