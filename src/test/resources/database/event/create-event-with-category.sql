INSERT INTO events (
    title, description_small, description_full, avatar_image, main_image, video_link,
    maximum_participants, date_of_start_event, price, created_at, city, google_map_coordinates,
    author_id, is_deleted, registration_available_until
) VALUES (
             'Sample Event',
             'Short description.',
             'Full description of the event.',
             'avatar.jpg',
             'main_image.jpg',
             'https://example.com/video',
             10,
             '2026-04-01 00:00:00',
             10,
             NOW(),
             'Odesa',
             '0.0,0.0',
             2,
             0,
             '2026-03-01 00:00:00'
         );

INSERT INTO categories (name, description, is_deleted)
VALUES ('Category one', 'Category one description', 0);

INSERT INTO events_categories (event_id, category_id)  VALUES (1, 1)
