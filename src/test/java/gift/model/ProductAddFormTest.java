package gift.model;

import static org.assertj.core.api.Assertions.assertThat;

import gift.form.ProductAddForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductAddFormTest {

    ValidatorFactory factory;
    Validator validator;
    int failCount;
    ProductAddForm form;

    @BeforeEach
    void before() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        failCount = 0;
        form = new ProductAddForm();
    }


    @Test
    void nameLength() {

        form.setPrice(100);
        form.setImageUrl("https://");

        //성공 길이: 1~15자
        form.setName("1"); isValidInput(form);
        form.setName("123456789012345"); isValidInput(form);
        assertThat(failCount).isEqualTo(0);

        //실패 길이: 0자, 16자 이상
        form.setName(""); isValidInput(form);
        form.setName("12345678901234567"); isValidInput(form);
        assertThat(failCount).isEqualTo(2);
    }

    @Test
    void nameNormalChar() {
        form.setPrice(100);
        form.setImageUrl("https://");

        form.setName("ㄱ"); isValidInput(form); form.setName("ㅎ"); isValidInput(form);
        form.setName("ㅏ"); isValidInput(form); form.setName("ㅣ"); isValidInput(form);
        form.setName("가"); isValidInput(form); form.setName("힣"); isValidInput(form);

        form.setName("a"); isValidInput(form); form.setName("z"); isValidInput(form);
        form.setName("A"); isValidInput(form); form.setName("Z"); isValidInput(form);

        form.setName("0"); isValidInput(form); form.setName("9"); isValidInput(form);

        form.setName("ㄱㅏㅎㅣ가힣azAZ09"); isValidInput(form);

        assertThat(failCount).isEqualTo(0);
    }

    @Test
    void nameSpecialChar() {
        form.setPrice(100);
        form.setImageUrl("https://");

        // 가능한 특수문자 입력
        form.setName("("); isValidInput(form); form.setName(")"); isValidInput(form); form.setName("["); isValidInput(form);
        form.setName("]"); isValidInput(form); form.setName("+"); isValidInput(form); form.setName("-"); isValidInput(form);
        form.setName("&"); isValidInput(form); form.setName("/"); isValidInput(form); form.setName("_"); isValidInput(form);
        form.setName("()[]+-&/_"); isValidInput(form);

        assertThat(failCount).isEqualTo(0);

        // 불가능한 특수문자 입력
        form.setName("#"); isValidInput(form); form.setName("~"); isValidInput(form); form.setName("@"); isValidInput(form);
        form.setName("$"); isValidInput(form); form.setName("%"); isValidInput(form); form.setName("^"); isValidInput(form);
        form.setName("*"); isValidInput(form); form.setName("="); isValidInput(form); form.setName("'"); isValidInput(form);
        form.setName(":"); isValidInput(form); form.setName(";"); isValidInput(form); form.setName("\""); isValidInput(form);
        form.setName("{"); isValidInput(form); form.setName("}"); isValidInput(form); form.setName("|"); isValidInput(form);
        form.setName("\\"); isValidInput(form); form.setName("<"); isValidInput(form); form.setName(">"); isValidInput(form);
        form.setName(","); isValidInput(form); form.setName("."); isValidInput(form); form.setName("?"); isValidInput(form);
        form.setName("!"); isValidInput(form);

        assertThat(failCount).isEqualTo(22);
    }

    @Test
    void nameKakao() {
        form.setPrice(100);
        form.setImageUrl("https://");

        form.setName("카카"); isValidInput(form);
        form.setName("카오"); isValidInput(form);
        form.setName("카오카"); isValidInput(form);
        assertThat(failCount).isEqualTo(0);

        form.setName("카카오"); isValidInput(form);
        form.setName("카카오카카오"); isValidInput(form);
        form.setName("카카오카"); isValidInput(form);
        assertThat(failCount).isEqualTo(3);
    }



    private void isValidInput(ProductAddForm form) {
        if(!validator.validate(form).isEmpty()) {
            failCount++;
            Set<ConstraintViolation<ProductAddForm>> violations = 
                validator.validate(form);
            for (ConstraintViolation<ProductAddForm> violation : violations) {
                System.out.println("violation.getMessage() = " + violation.getMessage());
            }
        }
    }

}