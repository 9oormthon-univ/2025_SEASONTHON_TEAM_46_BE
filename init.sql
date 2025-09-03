-- ENUM 타입 정의
CREATE TYPE IF NOT EXISTS news_category AS ENUM (
    'POLITICS', 'SOCIETY', 'ECONOMY', 'INTERNATIONAL', 'CULTURE', 'SPORTS', 'IT_SCIENCE'
);

CREATE TYPE IF NOT EXISTS sentiment AS ENUM (
    'HOPE_ENCOURAGE', 'ANGER_CRITICISM', 'ANXIETY_CRISIS', 'SAD_SHOCK', 'NEUTRAL_FACTUAL'
);

CREATE TYPE IF NOT EXISTS political_orientation AS ENUM (
    'PROGRESSIVE', 'CONSERVATIVE', 'MODERATE'
);



-- NEWS 테이블
CREATE TABLE IF NOT EXISTS news (
                                    id              BIGSERIAL PRIMARY KEY,
                                    outlet          VARCHAR(255),
    feed_url        TEXT,
    title           TEXT,
    author          VARCHAR(255),
    summary         TEXT,
    link            TEXT UNIQUE,
    published       VARCHAR(255),               -- ISO 8601 문자열 저장
    crawled_at      TIMESTAMP,
    category        news_category,
    sentiment       sentiment,
    confidence      DOUBLE PRECISION,
    rationale       TEXT,
    orientation     political_orientation,
    emotion_rating  DOUBLE PRECISION,           -- double → double precision
    thumbnail       TEXT,
    like_count      BIGINT,
    tagged_at       TIMESTAMP
    );


-- NEWS_IMAGE 테이블
CREATE TABLE IF NOT EXISTS news_image (
                                          id          BIGSERIAL PRIMARY KEY,
                                          news_id     BIGINT NOT NULL,
                                          src         TEXT NOT NULL,
                                          alt         TEXT,
                                          CONSTRAINT fk_news FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE
    );


-- 샘플 데이터 삽입
INSERT INTO news (
    id, outlet, feed_url, title, author, summary, link,
    published, crawled_at, category, sentiment, confidence,
    rationale, orientation, emotion_rating, thumbnail, like_count, tagged_at
) VALUES
      (
          2, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '“신세계면세점 폭파” 협박 글 쓴 30대 남성 긴급체포', NULL,
          '신세계백화점 본점에 폭발물을 설치했다는 위협 글이 온라인 커뮤니티에 게시된 지난달 5일 경찰특공대가 서울 명동 신세계백화점 내부를 수색하는 동안 현장이 통제되고 있다. 정효진 기자 SNS에 신세계면세점을 폭파하겠다는 내용의 협박 글을 올린 남성이 경찰에 붙잡혔다. 서울 남대문경찰서는 2일 인스타그램에 이 같은 협박 댓글을 게시한 혐의를 받는 30대 남성···',
          'https://www.khan.co.kr/article/202509022236001/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:13.954496', 'SOCIETY', 'ANGER_CRITICISM', 0.9,
          '폭발물 설치 협박 사건을 다루며 사회적 불안과 비판적인 감정을 유발하는 내용이다.',
          NULL, 0.0,
          'https://img.khan.co.kr/news/2025/09/02/news-p.v1.20250902.f4a5f579be17442ab7cc9fa5851bd715_P1.jpeg',
          NULL, '2025-09-02 15:42:53.224524'
      ),
      (
          1, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '미 법원 “트럼프 ‘LA 파병’은 연방법 위반”···주 방위군 배치에 제동', NULL,
          '도널드 트럼프 미국 대통령이 지닌달 14일 워싱턴DC 백악관 집무실에서 발언하고 있다. APF통신 미국 법원이 캘리포니아에 주방위군을 배치하겠다는 도널드 트럼프 대통령의 계획에 제동을 걸었다. 2일(현지시간) AP 통신에 따르면 미국 샌프란시스코 소재 연방 지방법원 찰스 브레이어 판사는 트럼프 행정부가 이민자 단속을 위해 군대를 파견한 것은 연방법 위반···',
          'https://www.khan.co.kr/article/202509022239001/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:13.753385', 'POLITICS', 'ANGER_CRITICISM', 0.9,
          '트럼프 대통령의 군대 파견 계획이 법원에 의해 위반으로 판결되며 정치적 논란과 비판을 불러일으키고 있다.',
          NULL, 0.0,
          'https://img.khan.co.kr/news/2025/09/02/news-p.v1.20250825.34db7e78a93b42f09fb736fc792c715c_P1.png',
          NULL, '2025-09-02 15:42:57.21759'
      ),
      (
          5, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '또 ‘민주당 텃밭’ 때리는 트럼프 “시카고는 세계 살인의 수도”···군 투입 재차 시사', NULL,
          '도널드 트럼프 미국 대통령. 게티이미지 도널드 트럼프 미국 대통령이 2일(현지시간) 일리노이주 시카고를 “세계 살인의 수도”라고 비난했다. 트럼프 대통령은 이날 트루스소셜에서 이같이 말했다. 트럼프 대통령은 다른 게시글에서도 “주말 동안 시카고에서 최소 54명이 총에 맞았고 그 중 8명이 사망했다. 지난 두 번의 주말도 비슷한 상황이었다”며 “시카고는···',
          'https://www.khan.co.kr/article/202509022208001/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:14.728249', 'POLITICS', 'ANGER_CRITICISM', 0.9,
          '트럼프 대통령의 비난과 군 투입 시사로 인해 정치적 논란과 비판이 일어나는 상황을 다룬 기사이다.',
          NULL, 0.0,
          'https://img.khan.co.kr/news/2025/09/02/news-p.v1.20250828.659dea170e474d28b0e1483676857061_P1.jpg',
          NULL, '2025-09-02 15:42:45.998402'
      ),
      (
          4, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '현대차 노조, 3일부터 3일간 부분파업 돌입', NULL,
          '‘7년 연속 무쟁의 교섭’ 깨져 현대자동차 노동조합이 임금 및 단체협약 개정 협상 난항에 항의하며 부분파업에 나선다. 현대차 노조의 파업은 7년 만이다. 2일 금속노조 현대차지부 등에 따르면 노조는 중앙쟁의대책위원회를 통해 3일부터 부분파업을 하기로 결정했다. 오전 출근조와 오후 출근조가 3일과 4일 각 2시간씩, 5일에는 4시간 동안 파업할 예···',
          'https://www.khan.co.kr/article/202509022213005/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:14.558147', 'SOCIETY', 'ANGER_CRITICISM', 0.9,
          '노조의 파업 결정과 관련된 불만과 갈등을 다룬 기사로, 노동자의 권리와 임금 인상 요구가 중심이다.',
          NULL, 0.0, NULL,
          NULL, '2025-09-02 15:42:48.515246'
      ),
      (
          3, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '이 대통령 “나도 의자·마이크 들고 다녔다” 험지 원외위원장들 독려', NULL,
          '이재명 대통령이 2일 서울 용산 대통령실에서 열린 제40회 국무회의에 참석해 발언하고 있다. 대통령실사진기자단 이재명 대통령이 2일 민주당 원외지역위원장들과의 만찬에서 “저도 (경기 성남)분당갑 원외지역위원장 출신”이라며 “지역 정치를 할 때 자리를 마련해주지 않으면 의자를 갖고 다녔고, 마이크를 안 주면 마이크를 들고 다녔다”고 말한 것으로 전해졌다. ···',
          'https://www.khan.co.kr/article/202509022234001/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:14.261739', 'POLITICS', 'HOPE_ENCOURAGE', 0.85,
          '대통령이 원외위원장들을 격려하며 긍정적인 메시지를 전달하는 내용이다.',
          NULL, 0.0,
          'https://img.khan.co.kr/news/2025/09/02/news-p.v1.20250902.644977d801434d52afe87c757b894283_P1.jpeg',
          NULL, '2025-09-02 15:42:50.417138'
      ),
      (
          8, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '[케이블·위성 하이라이트]2025년 9월 3일', NULL,
          '■ 영화 ■ 빅토리(OCN 무비즈 오후 4시40분) = 1999년 거제, 댄서가 되고 싶은 고등학생 필선과 그의 단짝 미나는 춤 연습실이 필요하다. 두 사람은 서울에서 전학 온 치어리더 세현을 설득해 치어리딩 동아리를 만든다. 부원 9명이 모여 탄생한 ‘밀레니엄 걸즈’의 첫 임무는 거제상고 축구부를 위한 치어리딩. 시장, 병원, 파업 현장 등 응원이 필요한···',
          'https://www.khan.co.kr/article/202509022148015/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:15.3231', 'CULTURE', 'HOPE_ENCOURAGE', 0.9,
          '영화와 예능 프로그램의 긍정적인 메시지와 도전 이야기를 다룬 기사이다.',
          NULL, 0.0, NULL,
          NULL, '2025-09-02 15:42:33.229618'
      ),
      (
          10, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '김하성, 탬파베이서 애틀랜타로 ‘전격 이적’…내년 시즌 ‘가을야구’ 꿈꾼다', NULL,
          '‘간판’들 부상 불구 기본 전력 탄탄 유격수 공백 메워 ‘2026 대권 도전’ 올시즌 미국프로야구 메이저리그 탬파베이에서 뛰던 김하성(30·사진)이 애틀랜타로 전격 이적했다. 메이저리그 공식 홈페이지는 2일 “탬파베이가 김하성을 웨이버 공시했고 애틀랜타가 김하성을 영입했다”고 밝혔다. 김하성은 3일 시카고 컵스 원정경기부터 새 팀에 합류한다.···',
          'https://www.khan.co.kr/article/202509022147035/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:15.65452', 'SPORTS', 'HOPE_ENCOURAGE', 0.85,
          '김하성의 이적 소식은 새로운 팀에서의 가능성을 보여주며 긍정적인 미래를 암시한다.',
          NULL, 0.0,
          'https://img.khan.co.kr/news/2025/09/02/l_2025090301000117300009301.jpg',
          NULL, '2025-09-02 15:42:27.989542'
      ),
      (
          9, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '‘9월 모평’ 특징 짚고 막바지 수능 전략 짠다', NULL,
          'EBS1 ‘생방송 9월 모의평가 분석’ 9월 모의평가는 2026학년도 대학수학능력시험(수능) 전 마지막 실전 연습이자 수능의 난도와 출제 방향을 가늠할 수 있는 지표라는 점에서 전국 대입 수험생들과 교사, 학부모들에게 중요하다. 3일 EBS 1TV는 이날 진행되는 모의평가를 실시간 분석하는 <특별생방송 9월 모의평가 분석>을 3부에 걸쳐 내···',
          'https://www.khan.co.kr/article/202509022148005/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:15.488321', 'SOCIETY', 'HOPE_ENCOURAGE', 0.9,
          '수능 준비 전략과 수험생 정신건강 관리법을 다루며 긍정적인 메시지를 전달하는 기사이다.',
          NULL, 0.0, NULL,
          NULL, '2025-09-02 15:42:30.73844'
      ),
      (
          7, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '[TV 하이라이트]2025년 9월 3일', NULL,
          '인간 때문에 ‘멸종위기’라는데… ■Why?(KBS2 오후 5시) = 과학을 좋아하는 ‘엄지’와 ‘꼼지’가 지구의 동식물을 만나며 환경에 대해 배운다. 3일 방송에서는 위기에 처한 동물들을 살펴본다. 정형행동은 동물이 극심한 스트레스를 받을 때 보이는 반복적이고 지속적인 행동이다. 동물원에 갇혀 정형행동을 하는 동물과 날지 못하는 새가 정말 행복할지 생···',
          'https://www.khan.co.kr/article/202509022148025/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:15.144272', 'IT_SCIENCE', 'NEUTRAL_FACTUAL', 0.8,
          '환경 문제와 동물의 멸종 위기를 다룬 과학적 내용을 전달하는 기사이다.',
          NULL, 0.0, NULL,
          NULL, '2025-09-02 15:42:36.305493'
      ),
      (
          6, '경향신문', 'https://www.khan.co.kr/rss/rssdata/total_news.xml',
          '인니 노조, 반정부 시위 격화에 “상황 안정될 때까지 집회 전면 연기”', NULL,
          '2일(현지시간) 인도네시아 자카르타에서 시민들이 최근 격화한 반정부 시위로 불에 탄 버스정류장 앞에 서 있다. 로이터연합뉴스 인도네시아에서 국회의원이 받는 과도한 주택 수당에 항의하는 시위가 격화하자 주요 노동조합들이 당분간 집회를 열지 않기로 합의했다. 대규모 인원이 모일 경우 폭력 시위로 비화해 사회 혼란이 가중될 우려를 사전에 막기 위한 조처다. ···',
          'https://www.khan.co.kr/article/202509022149001/?utm_source=khan_rss&utm_medium=rss&utm_campaign=total_news',
          NULL, '2025-09-02 15:42:14.96619', 'SOCIETY', 'ANXIETY_CRISIS', 0.9,
          '반정부 시위와 관련된 폭력 사태와 사회 혼란을 다루며 불안과 위기감을 조성하는 내용이다.',
          NULL, 0.0,
          'https://img.khan.co.kr/news/2025/09/02/rcv.YNA.20250902.PRU20250902192901009_P1.jpg',
          NULL, '2025-09-02 15:42:39.515308'
      );

-- NEWS_IMAGE 테이블에 샘플 이미지 데이터 삽입
INSERT INTO news_image (id, news_id, src, alt) VALUES
                                                   (1, 1,
                                                    'https://img.khan.co.kr/news/2025/09/02/news-p.v1.20250825.34db7e78a93b42f09fb736fc792c715c_P1.png',
                                                    '도널드 트럼프 미국 대통령이 지닌달 14일 워싱턴DC 백악관 집무실에서 발언하고 있다. APF통신'
                                                   ),
                                                   (2, 2,
                                                    'https://img.khan.co.kr/news/2025/09/02/news-p.v1.20250902.f4a5f579be17442ab7cc9fa5851bd715_P1.jpeg',
                                                    '신세계백화점 본점에 폭발물을 설치했다는 위협 글이 온라인 커뮤니티에 게시된 지난달 5일 경찰특공대가 서울 명동 신세계백화점 내부를 수색하는 동안 현장이 통제되고 있다. 정효진 기자'
                                                   ),
                                                   (3, 3,
                                                    'https://img.khan.co.kr/news/2025/09/02/news-p.v1.20250902.644977d801434d52afe87c757b894283_P1.jpeg',
                                                    '이재명 대통령이 2일 서울 용산 대통령실에서 열린 제40회 국무회의에 참석해 발언하고 있다. 대통령실사진기자단'
                                                   ),
                                                   (4, 5,
                                                    'https://img.khan.co.kr/news/2025/09/02/news-p.v1.20250828.659dea170e474d28b0e1483676857061_P1.jpg',
                                                    '도널드 트럼프 미국 대통령. 게티이미지'
                                                   ),
                                                   (5, 6,
                                                    'https://img.khan.co.kr/news/2025/09/02/rcv.YNA.20250902.PRU20250902192901009_P1.jpg',
                                                    '2일(현지시간) 인도네시아 자카르타에서 시민들이 최근 격화한 반정부 시위로 불에 탄 버스정류장 앞에 서 있다. 로이터연합뉴스'
                                                   ),
                                                   (6, 6,
                                                    'https://img.khan.co.kr/news/2025/08/31/khan_nyP9KC.jpeg',
                                                    ''
                                                   ),
                                                   (7, 10,
                                                    'https://img.khan.co.kr/news/2025/09/02/l_2025090301000117300009301.jpg',
                                                    '김하성, 탬파베이서 애틀랜타로 ‘전격 이적’…내년 시즌 ‘가을야구’ 꿈꾼다'
                                                   );

