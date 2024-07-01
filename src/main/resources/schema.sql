DROP TABLE IF EXISTS PRODUCTS;
CREATE TABLE PRODUCTS (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY,
  name VARCHAR(255) NOT NULL,
  price INT NOT NULL,
  image_url VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
)
