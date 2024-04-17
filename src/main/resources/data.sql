-- Inserir usuários
INSERT INTO TB_ADMIN_ESTOQUISTA (EMAIL, NAME, CPF, PASSWORD, GROUP_NAME, IS_ACTIVE)
VALUES
    ('ana@example.com', 'Ana Silva', '111.111.111-11', 'senha123', 'admin', true),
    ('joao@example.com', 'João Santos', '222.222.222-22', 'senha456', 'estoquista', true),
    ('maria@example.com', 'Maria Oliveira', '333.333.333-33', 'senha789', 'admin', true);

-- Inserir serviços de assinatura de jogos
INSERT INTO TB_PRODUCT (PRODUCT_NAME, PRICE, DESCRIPTION, RATING, STORAGE, IS_ACTIVE)
VALUES
    ('Xbox Game Pass', 229.99, 'Experimente uma vasta biblioteca de jogos de alta qualidade com o Xbox Game Pass. De títulos de grande sucesso a joias indie, descubra seu próximo jogo favorito com acesso ilimitado. Aproveite os lançamentos no primeiro dia, descontos exclusivos e jogos multijogador em consoles Xbox e PC.', 4.7, 10, true),
    ('PlayStation Now', 224.99, 'Mergulhe em uma extensa coleção de sucessos do PlayStation, franquias de grande sucesso e clássicos atemporais com o PlayStation Now. Transmita ou baixe jogos para o seu PS4 ou PC e desfrute de acesso instantâneo a uma variedade diversificada de experiências de jogos.', 4.6, 6, true),
    ('EA Play', 399.99, 'Prepare-se para jogar com o EA Play. Aproveite acesso antecipado exclusivo a títulos futuros, jogo ilimitado de uma coleção dos melhores jogos da EA e benefícios exclusivos para membros, incluindo recompensas dentro do jogo, desafios e conteúdo exclusivo.', 4.5, 46, true),
    ('Xbox Live Gold', 399.98, 'Desbloqueie todo o potencial da sua experiência Xbox com o Xbox Live Gold. Tenha acesso a um mundo de jogos multijogador, jogos gratuitos todos os meses, descontos exclusivos para membros e muito mais. Junte-se à comunidade global de jogadores de Xbox e leve sua jogabilidade para o próximo nível.', 4.4, 2, true),
    ('PlayStation Plus', 228.99, 'Junte-se à comunidade PlayStation Plus e eleve sua experiência de jogo nos consoles PlayStation. Desfrute de acesso ao multijogador online, jogos gratuitos mensais, descontos exclusivos e armazenamento na nuvem para seus jogos salvos. Descubra novas aventuras e conecte-se com jogadores ao redor do mundo.', 4.8, 69, true),
    ('Nintendo Switch Online', 499.99, 'Aprimore sua experiência no Nintendo Switch com o Nintendo Switch Online. Jogue seus jogos favoritos da Nintendo online, acesse uma biblioteca crescente de títulos clássicos do NES e SNES e aproveite ofertas exclusivas para membros. Proteja seus dados de jogos com backup na nuvem e mergulhe em um mundo de jogos online e clássicos retrô.', 4.3, 22, true);

-- Relacionar imagens aos serviços de assinatura
INSERT INTO TB_PRODUCT_IMAGES (PRODUCT_ID, IMAGE_PATH)
VALUES
    (1, 'https://imgur.com/byxykHj.png'),
    (1, 'https://imgur.com/wcauRug.png'),
    (2, 'https://imgur.com/S9NB3BT.png'),
    (2, 'https://imgur.com/PMFnQA1.png'),
    (3, 'https://imgur.com/IP9EhAg.png'),
    (3, 'https://imgur.com/J9BJSpc.png'),
    (4, 'https://imgur.com/bdXV2Tb.png'),
    (4, 'https://imgur.com/d3Psse0.png'),
    (5, 'https://imgur.com/YqeHNym.png'),
    (5, 'https://imgur.com/T5ENXL8.png'),
    (6, 'https://imgur.com/PciLoHK.png'),
    (6, 'https://imgur.com/VSi9cTs.png');
