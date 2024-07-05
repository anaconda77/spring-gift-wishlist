package gift.repository;

import gift.model.Product;
import gift.model.WishProduct;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishProductDao {

    private final JdbcTemplate jdbcTemplate;

    public WishProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll(Long memberId) {
        var sql = "SELECT * FROM products WHERE id"
            + " in (SELECT product_id FROM wish_products WHERE member_id = ?)";
        return jdbcTemplate.query(sql, productRowMapper, memberId);
    }




    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getInt("price"),
        rs.getString("image_url")
    );

}