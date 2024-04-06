INSERT INTO user_info (id, email, password, role, verification_code, verification_code_expiration)
VALUES
    (1,'admin@gmail.com', '$2a$10$Z8tC3Q5Usas8WeWV.CcJquryNpNiM0pm7cL7FvxN.NfsIQ9EBUqGG', 'ADMIN', NULL, NULL),
    (2,'mkerkibasheva@gmail.com', '$2a$10$Z8tC3Q5Usas8WeWV.CcJquryNpNiM0pm7cL7FvxN.NfsIQ9EBUqGG', 'USER', NULL, NULL),
    (3,'aakhunova02@gmail.com', '$2a$10$Z8tC3Q5Usas8WeWV.CcJquryNpNiM0pm7cL7FvxN.NfsIQ9EBUqGG', 'USER', NULL, NULL),
    (4,'adelyajyldyzbekova@gmail.com', '$2a$10$Z8tC3Q5Usas8WeWV.CcJquryNpNiM0pm7cL7FvxN.NfsIQ9EBUqGG', 'USER', NULL, NULL),
    (5,'useraccount@gmail.com', '$2a$10$Z8tC3Q5Usas8WeWV.CcJquryNpNiM0pm7cL7FvxN.NfsIQ9EBUqGG', 'USER', NULL, NULL);


INSERT INTO users (id, first_name, last_name, user_info_id)
VALUES
    (1, 'Admin', 'Admin',1),
    (2, 'Meri', 'Kerkibasheva',2),
    (3, 'Alina', 'Ahunova',3),
    (4, 'Adel', 'Zhyldyzbekova',4),
    (5, 'Ainazik', 'Toktomamatova',5);


INSERT INTO test (id, title, short_description, enable, duration, is_passed)
VALUES
    (1, 'English Grammar', 'Test on English grammar', true, 27, true),
    (2, 'Vocabulary', 'Test on English vocabulary', true, 27, true),
    (3, 'Listening', 'Test on English vocabulary', true, 27, false);


INSERT INTO question (id, attempts, correct_answer, duration, enable, file_url, passage, question_type, statement, title, test_id)
VALUES
    (1, 0, 'In the center of the clearing stands a man who is enjoying the beauty of the winter forest. He is dressed in a warm jacket and hat, and his breath is visible in the cold air.', 3, true, NULL, 'In this winter painting, you can see snowflakes slowly falling onto the ground, creating a magical atmosphere. In the center of the clearing stands a man who is enjoying the beauty of the winter forest. He is dressed in a warm jacket and hat, and his breath is visible in the cold air. Next to him, hiding behind a fir tree, is an owl that seems very happy and о at the sight of the man. Its soft feathers and bright eyes create a feeling of warmth and coziness. Around the clearing, you can see snowdrifts and snowy trees that create a sense of depth and perspective. Overall, this is a very beautiful and calming painting that conveys the atmosphere of winter magic and warmth.', 'HIGHLIGHTS_THE_ANSWER', 'Who is the central figure in the painting and how is the man dressed', 'Highlight the answer', 1),
    (2, 0, NULL, 3, true, NULL, 'Marmalade is usually made from citrus fruit like oranges. The process to make marmalade involves the juice and peel of citrus fruits boiled with sugar and water. Typically, marmalade is clearer in colour, and it has a chunky texture from the pieces of included peel.' , 'SELECT_MAIN_IDEA', NULL, 'Select the main idea', 1),
    (3, 0, 'This is the picture of nature and squirrel with photo', 3, true, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709836862328nature.jpg',  NULL, 'DESCRIBE_IMAGE', NULL, 'Describe the image', 1),
    (4, 0, NULL, 3, true, NULL,  NULL, 'SELECT_REAL_ENGLISH_WORD', NULL, 'Select the real english words', 1),
    (5, 2, 'This place is beautiful in its own way', 3, true, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839630482audio2.ogg',  NULL, 'TYPE_WHAT_YOU_HEAR', NULL, 'Type what you hear', 1),
    (6, 0, NULL, 3,  true, NULL, NULL, 'LISTEN_AND_SELECT_WORD', NULL, 'Listen and select the words', 1),
    (7, 5, NULL, 3, true, NULL, NULL, 'RESPOND_IN_AT_LEAST_N_WORDS', 'What is your favorite cuisine?', 'Respond in at least N words', 1),
    (8, 0, 'This place is beautiful in its own way', 3, true, NULL, NULL, 'RECORD_SAYING', 'This place is beautiful in its own way', 'Record yourself saying the statement below', 1),
    (9, 0, NULL, 3, true, NULL, 'Books play a significant role in our life, especially for children.Reading books increases the knowledge of students, improves their intellect, makes students aware of the various societies, and civilizations across the globe. Moreover, reading books enhances imagination and creativity in the student''s mind.', 'SELECT_THE_BEST_TITLE', NULL, 'Select the best title', 1),
    (10, 0, 'In the center of the clearing stands a man who is enjoying the beauty of the winter forest. He is dressed in a warm jacket and hat, and his breath is visible in the cold air.', 1, true, NULL, 'In this winter painting, you can see snowflakes slowly falling onto the ground, creating a magical atmosphere. In the center of the clearing stands a man who is enjoying the beauty of the winter forest. He is dressed in a warm jacket and hat, and his breath is visible in the cold air. Next to him, hiding behind a fir tree, is an owl that seems very happy and о at the sight of the man. Its soft feathers and bright eyes create a feeling of warmth and coziness. Around the clearing, you can see snowdrifts and snowy trees that create a sense of depth and perspective. Overall, this is a very beautiful and calming painting that conveys the atmosphere of winter magic and warmth.', 'HIGHLIGHTS_THE_ANSWER', 'Who is the central figure in the painting and how is the man dressed', 'Highlight the answer', 2),
    (11, 0, NULL, 3, true, NULL, 'Foreign language study enhances listening skills and memory. One participates more effectively and responsibly in a multi-cultural world if one knows another language. Your marketable skills in the global economy are improved if you master another language.' , 'SELECT_MAIN_IDEA', NULL, 'Select the main idea', 2),
    (12, 0, 'I can see the statue of Liberty and Freedom', 3, true, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709837224015statue.webp',  NULL, 'DESCRIBE_IMAGE', NULL, 'Describe the image', 2),
    (13, 0, NULL, 3, true, NULL,  NULL, 'SELECT_REAL_ENGLISH_WORD', NULL, 'Select the real english words', 2),
    (14, 2, 'The true, the good, and the beautiful', 3,  true, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709840607100tre true.ogg',  NULL, 'TYPE_WHAT_YOU_HEAR', NULL, 'Type what you hear', 2),
    (15, 0, NULL, 3,  true, NULL, NULL, 'LISTEN_AND_SELECT_WORD', NULL, 'Listen and select the words', 2),
    (16, 5, NULL, 3, true, NULL, NULL, 'RESPOND_IN_AT_LEAST_N_WORDS', 'What are your Hobbies?', 'Respond in at least N words', 2),
    (17, 0, 'This place is beautiful in its own way', 2, true, NULL, NULL, 'RECORD_SAYING', 'This place is beautiful in its own way', 'Record yourself saying the statement below', 2),
    (18, 0, NULL, 3, true, NULL, 'Marmalade is usually made from citrus fruit like oranges. The process to make marmalade involves the juice and peel of citrus fruits boiled with sugar and water. Typically, marmalade is clearer in colour, and it has a chunky texture from the pieces of included peel.', 'SELECT_THE_BEST_TITLE', NULL, 'Select the best title', 2),

    (19, 0, 'In the center of the clearing stands a man who is enjoying the beauty of the winter forest. He is dressed in a warm jacket and hat, and his breath is visible in the cold air.', 3, true, NULL, 'In this winter painting, you can see snowflakes slowly falling onto the ground, creating a magical atmosphere. In the center of the clearing stands a man who is enjoying the beauty of the winter forest. He is dressed in a warm jacket and hat, and his breath is visible in the cold air. Next to him, hiding behind a fir tree, is an owl that seems very happy and о at the sight of the man. Its soft feathers and bright eyes create a feeling of warmth and coziness. Around the clearing, you can see snowdrifts and snowy trees that create a sense of depth and perspective. Overall, this is a very beautiful and calming painting that conveys the atmosphere of winter magic and warmth.', 'HIGHLIGHTS_THE_ANSWER', 'Who is the central figure in the painting and how is the man dressed', 'Highlight the answer', 3),
    (20, 0, NULL, 3, true, NULL, 'Books play a significant role in our life, especially for children. Reading books increases the knowledge of students, improves their intellect, makes students aware of the various societies, and civilizations across the globe. Moreover, reading books enhances imagination and creativity in the student''s mind.' , 'SELECT_MAIN_IDEA', NULL, 'Select the main idea', 3),
    (21, 0, 'This is the picture of nature and squirrel with photo', 3, true, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709836862328nature.jpg',  NULL, 'DESCRIBE_IMAGE', NULL, 'Describe the image', 3),
    (22, 0, NULL, 3, true, NULL,  NULL, 'SELECT_REAL_ENGLISH_WORD', NULL, 'Select the real english words', 3),
    (23, 2, 'This place is beautiful in its own way', 11, true, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839630482audio2.ogg',  NULL, 'TYPE_WHAT_YOU_HEAR', NULL, 'Type what you hear', 3),
    (24, 0, NULL, 3, true, NULL, NULL, 'LISTEN_AND_SELECT_WORD', NULL, 'Listen and select the words', 3),
    (25, 5, NULL, 3, true, NULL, NULL, 'RESPOND_IN_AT_LEAST_N_WORDS', 'What is your favorite cuisine?', 'Respond in at least N words', 3),
    (26, 0, 'This place is beautiful in its own way', 3, true, NULL, NULL, 'RECORD_SAYING', 'This place is beautiful in its own way', 'Record yourself saying the statement below', 3),
    (27, 0, NULL, 3, true, NULL, 'Marmalade is usually made from citrus fruit like oranges. The process to make marmalade involves the juice and peel of citrus fruits boiled with sugar and water. Typically, marmalade is clearer in colour, and it has a chunky texture from the pieces of included peel.', 'SELECT_THE_BEST_TITLE', NULL, 'Select the best title', 3);


INSERT INTO option (id, file_url, is_true_option, title, question_id)
VALUES
    (1, NULL, false, 'I have to complete my tasks before tomorrow', 2),
    (2, NULL, false, 'The little girl is playing around with toys', 2),
    (3, NULL, false, 'The main idea is about how people should strive for education', 2),
    (4, NULL, true, 'The main idea of the passage is how to make marmalade', 2),
    (5, NULL, true, 'Cousin', 4),
    (6, NULL, true, 'Mountain', 4),
    (7, NULL, true, 'Adventure', 4),
    (8, NULL, false, 'Beatiful', 4),
    (9, NULL, false, 'Ventur', 4),
    (10, NULL, false, 'Regretter', 4),
    (11, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839245515audio_2024-03-08_01-20-30.ogg', true, 'Option 1', 6),
    (12, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709840121506familiar.ogg', true, 'Option 2', 6),
    (13, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709842325808wjndscjk.ogg', false, 'Option 3', 6),
    (14, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839711616audio3.ogg', true, 'Option 4', 6),
    (15, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709842478198bonjour.ogg', false, 'Option 5', 6),
    (16, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709842478198bonjour.ogg', false, 'Option 5', 6),
    (17, NULL, true, 'I have to complete my tasks before tomorrow', 9),
    (18, NULL, false, 'The little girl is playing around with toys', 9),
    (19, NULL, false, 'The main idea is about how people should strive for education', 9),
    (20, NULL, false, 'The main idea of the text is that books hold significant value in our lives, particularly for children, by fostering knowledge, intellect, cultural awareness, and creativity.', 9),
    (21, NULL, false, 'Marmalade is usually made from citrus fruit like oranges.', 11),
    (22, NULL, false, 'How to protect the eyes from electronics', 11),
    (23, NULL, false, 'Having a library is important in our century', 11),
    (24, NULL, true, 'The main idea of the text is that studying a foreign language offers benefits such as enhanced listening skills, memory improvement, and increased effectiveness and responsibility in a multicultural world', 11),
    (25, NULL, true, 'Pedestrian', 13),
    (26, NULL, true, 'Sight', 13),
    (27, NULL, true, 'Clothes', 13),
    (28, NULL, false, 'Clothe', 13),
    (29, NULL, false, 'Dres', 13),
    (30, NULL, false, 'Bonjour', 13),
    (31, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709842325808wjndscjk.ogg', false, 'Option 1', 15),
    (32, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1711294440137audio_human.ogg', true, 'Option 2', 15),
    (33, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1711294745777audio_heyNotCorrect.ogg', false, 'Option 3', 15),
    (34, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1711294537416audio_school.ogg', true, 'Option 4', 15),
    (35, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1711294627761audio_achievement.ogg', true, 'Option 5', 15),
    (36, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709842478198bonjour.ogg', false, 'Option 6', 15),
    (37, NULL, true, 'I have to complete my tasks before tomorrow', 18),
    (38, NULL, false, 'The little girl is playing around with toys', 18),
    (39, NULL, false, 'The main idea is about how people should strive for education', 18),
    (40, NULL, false, 'The main idea of the passage is how to make marmalade', 18),
    (41, NULL, false, 'I have to complete my tasks before tomorrow', 20),
    (42, NULL, false, 'The little girl is playing around with toys', 20),
    (43, NULL, false, 'The main idea is about how people should strive for education', 20),
    (44, NULL, true, 'The main idea of the text is that books hold significant value in our lives, particularly for children, by fostering knowledge, intellect, cultural awareness, and creativity.', 20),
    (45, NULL, true, 'Cousin', 22),
    (46, NULL, true, 'Mountain', 22),
    (47, NULL, true, 'Adventure', 22),
    (48, NULL, false, 'Beatiful', 22),
    (49, NULL, false, 'Ventur', 22),
    (50, NULL, false, 'Regretter', 22),
    (51, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839245515audio_2024-03-08_01-20-30.ogg', true, 'Option 1', 24),
    (52, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709840121506familiar.ogg', true, 'Option 2', 24),
    (53, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709842325808wjndscjk.ogg', false, 'Option 3', 24),
    (54, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839711616audio3.ogg', true, 'Option 4', 24),
    (55, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709842478198bonjour.ogg', false, 'Option 5', 24),
    (56, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709842478198bonjour.ogg', false, 'Option 5', 24),
    (57, NULL, true, 'I have to complete my tasks before tomorrow', 27),
    (58, NULL, false, 'The little girl is playing around with toys', 27),
    (59, NULL, false, 'The main idea is about how people should strive for education', 27),
    (60, NULL, false, 'The main idea of the passage is how to make marmalade', 27);


INSERT INTO result (id, date_of_submission, is_seen, score, status, test_id, user_id)
VALUES
    (1, '2024-01-01T12:00:00', true, 76, 'EVALUATED', 1, 2),
    (2, '2024-01-01T12:00:00', true, 70, 'EVALUATED', 1, 3),
    (3, '2024-01-01T12:00:00', false, 0, 'NOT_EVALUATED', 2, 4),
    (4, '2024-01-01T12:00:00', false, 0, 'NOT_EVALUATED', 2, 5);


INSERT INTO answer (id, attempts, audio_file, data, date_of_submission, is_checked, score, status, question_id, results_id, user_id)
VALUES
    (1, 0, NULL, 'In the center of the clearing stands a man who is enjoying the beauty of the winter forest. He is dressed in a warm jacket and hat, and his breath is visible in the cold air.', '2024-01-01T12:15:00', true, 10, 'EVALUATED', 1, 1, 2),
    (2, 0, NULL, NULL, '2024-01-02T15:00:00', true, 0, 'EVALUATED', 2, 1, 2),
    (3, 0, NULL, 'This is the picture of nature and squirrel with photo', '2024-01-03T11:00:00', true, 10, 'EVALUATED', 3, 1, 2),
    (4, 0, NULL, NULL, '2024-01-04T18:45:00', true, 10, 'EVALUATED', 4, 1, 2),
    (5, 2, NULL, 'This place is beautiful in its own way', '2024-02-07T18:20:00', true, 10, 'EVALUATED', 5, 1, 2),
    (6, 0, NULL, NULL, '2024-01-06T13:00:00', true, 8, 'EVALUATED', 6, 1, 2),
    (7, 50, NULL, 'My favourite European cuisine are Italian and Spanish. They both are easy for most tastes, use the best of the Mediterranian ingredients, and have a vast repertoire of recipes. And they both are very healthy. Despite the popularity of French cuisine, and although I find it is very tasty and elaborated, I dislike the common abusive use of butter and cream, which conceals the real taste of the raw ingredients.', '2024-01-07T16:30:00', true, 10, 'EVALUATED', 7, 1, 2),
    (8, 0, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839630482audio2.ogg', NULL, '2024-01-08T10:10:00', true, 10, 'EVALUATED', 8, 1, 2),
    (9, 0, NULL, NULL, '2024-01-09T22:00:00', true, 10, 'EVALUATED', 9, 1, 2),
    (10, 0, NULL, 'In the center of the clearing stands a man who is enjoying the beauty of the winter forest.', '2024-01-01T12:15:00', true, 4, 'EVALUATED', 1, 2, 3),
    (11, 0, NULL, NULL, '2024-01-02T15:00:00', true, 0, 'EVALUATED', 2, 2, 3),
    (12, 0, NULL, 'This is the picture of nature', '2024-01-03T11:00:00', true, 10, 'EVALUATED', 3, 2, 3),
    (13, 0, NULL, NULL, '2024-01-04T18:45:00', true, 10, 'EVALUATED', 4, 2, 3),
    (14, 2, NULL, 'Hello, i am backend developer', '2024-02-07T18:20:00', true, 10, 'EVALUATED', 5, 2, 3),
    (15, 0, NULL, NULL, '2024-01-06T13:00:00', true, 6, 'EVALUATED', 6, 2, 3),
    (16, 50, NULL, 'It''s almost impossible to say which one I love most. I''m a foodie and I appreciate any type of food that is prepared with love . However, if I was forced to pick one, I''d probably still go with Italian. It''s a very light and healthy cuisine that is very passionate at the same time. It''s especially great during the warm season. For example there is nothing better to eat on a hot summer evening than a homemade Caprese salad (cherry tomatoes, buffalo mozzarella, basil leaves, a drizzle of extra virgin olive oil and some fresh bread as a side).', '2024-01-07T16:30:00', true, 10, 'EVALUATED', 7, 2, 3),
    (17, 0, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839630482audio2.ogg', NULL, '2024-01-08T10:10:00', true, 10, 'EVALUATED', 8, 2, 3),
    (18, 0, NULL, NULL, '2024-01-09T22:00:00', true, 10, 'EVALUATED', 9, 2, 3),

    (19, 0, NULL, 'Through Project Amber, the Java programming language is evolving faster than ever.', '2024-01-01T12:15:00', false, 0, 'NOT_EVALUATED', 10, 3, 4),
    (20, 0, NULL, NULL, '2024-01-02T15:00:00', true, 10, 'EVALUATED', 11, 3, 4),
    (21, 0, NULL, 'I can see the statue of Liberty and Freedom', '2024-01-03T11:00:00', false, 0, 'NOT_EVALUATED', 12, 3, 4),
    (22, 0, NULL, NULL, '2024-01-04T18:45:00', true, 10, 'EVALUATED', 13, 3, 4),
    (23, 2, NULL, 'The true, the good, and the beautiful', '2024-02-07T18:20:00', true, 10, 'EVALUATED', 14, 3, 4),
    (24, 0, NULL, NULL, '2024-01-06T13:00:00', true, 7, 'EVALUATED', 15, 3, 4),
    (25, 50, NULL, 'I usually spend my leisure time reading books or playing badminton. I like reading new and trending novels and my favourite genres include suspense, horror and thriller novels. Being a voracious reader, I believe that I have a strong vocabulary and knowledge of grammar. My zeal for writing gets its flair from my passion for reading books.', '2024-01-07T16:30:00', false, 0, 'NOT_EVALUATED', 16, 3, 4),
    (26, 0, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839630482audio2.ogg', NULL, '2024-01-08T10:10:00', false, 0, 'NOT_EVALUATED', 17, 3, 4),
    (27, 0, NULL, NULL, '2024-01-09T22:00:00', true, 10, 'EVALUATED', 18, 3, 4),

    (28, 0, NULL, 'Through Project Amber, the Java programming language is evolving faster than ever.', '2024-01-01T12:15:00', false, 0, 'NOT_EVALUATED', 10, 4, 5),
    (29, 0, NULL, NULL, '2024-01-02T15:00:00', true, 10, 'EVALUATED', 11, 4, 5),
    (30, 0, NULL, 'I can see the statue of Liberty and Freedom', '2024-01-03T11:00:00', false, 0, 'NOT_EVALUATED', 12, 4, 5),
    (31, 0, NULL, NULL, '2024-01-04T18:45:00', true, 7, 'EVALUATED', 13, 4, 5),
    (32, 2, NULL, 'The true, the good, and the beautiful', '2024-02-07T18:20:00', true, 10, 'EVALUATED', 14, 4, 5),
    (33, 0, NULL, NULL, '2024-01-06T13:00:00', true, 10, 'EVALUATED', 15, 4, 5),
    (34, 48, NULL, 'My hobbies are reading books and working out. Along with this, I also like cooking. While researching the company, I got to know about the in-house gym. Having a gym in the workplace is a great idea as employees can get to know each other better on a casual level apart from their designated roles.', '2024-01-07T16:30:00', false, 0, 'NOT_EVALUATED', 16, 4, 5),
    (35, 0, 'https://bilingualsbacket.s3.ap-northeast-2.amazonaws.com/1709839630482audio2.ogg', NULL, '2024-01-08T10:10:00', false, 0, 'NOT_EVALUATED', 17, 4, 5),
    (36, 0, NULL, NULL, '2024-01-09T22:00:00', true, 10, 'EVALUATED', 18, 4, 5);


INSERT INTO answer_options (answer_id, options_id)
VALUES
    (2, 3),
    (4, 5),
    (4, 6),
    (4, 8),
    (6, 11),
    (6, 12),
    (9, 17),
    (11, 3),
    (13, 5),
    (13, 6),
    (13, 8),
    (15, 11),
    (15, 12),
    (18, 17),
    (20, 24),
    (22, 25),
    (22, 26),
    (22, 27),
    (24, 32),
    (24, 33),
    (24, 34),
    (27, 33),
    (29, 24),
    (31, 25),
    (31, 26),
    (31, 27),
    (33, 32),
    (33, 34),
    (33, 35),
    (36, 37);