package duy.packages.utils;

import java.util.regex.Pattern;

/**
 * Lop tien ich validate du lieu nhap tu FORM (dung chung cho tat ca Controller).
 * Muc dich: kiem tra du lieu phia SERVER truoc khi luu xuong Database,
 * khong phu thuoc/tin tuong hoan toan vao validate phia client (HTML5/JS)
 * vi nguoi dung co the tat JS hoac goi thang request.
 */
public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    // Cho phep chu, so, dau gach duoi, dai 4-50 ky tu
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_]{4,50}$");

    // So dien thoai VN: bat dau bang 0, 9-11 chu so
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^0[0-9]{9,10}$");

    private static final Pattern OTP_PATTERN =
            Pattern.compile("^[0-9]{6}$");

    private ValidationUtils() {
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return !isBlank(email) && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidUsername(String username) {
        return !isBlank(username) && USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    /** Mat khau toi thieu 6 ky tu (co the mo rong them quy tac chu hoa/so neu can). */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isValidPhone(String phone) {
        // Cho phep bo trong (khong bat buoc), neu co nhap thi phai dung dinh dang
        if (isBlank(phone)) return true;
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    public static boolean isValidOtp(String otp) {
        return !isBlank(otp) && OTP_PATTERN.matcher(otp.trim()).matches();
    }

    /** Chuoi so thuc, > 0 (dung cho gia san pham). Tra ve false neu parse loi. */
    public static boolean isPositiveNumber(String value) {
        if (isBlank(value)) return false;
        try {
            return Double.parseDouble(value.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** So nguyen hop le va > 0 (dung cho id danh muc...). */
    public static boolean isPositiveInt(String value) {
        if (isBlank(value)) return false;
        try {
            return Integer.parseInt(value.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean maxLength(String value, int max) {
        return value == null || value.length() <= max;
    }
}
