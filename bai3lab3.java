import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class bai3lab3 {
    // Hàm kiểm tra số nguyên tố
    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Hàm tính tổng các số nguyên tố nhỏ hơn N
    public static long sumPrimesLessThanN(int n) {
        long sum = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                sum += i;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Nhập số N từ hộp thoại
            String input = JOptionPane.showInputDialog(
                null,
                "Nhập vào số nguyên dương N:",
                "Tính tổng số nguyên tố < N",
                JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                // Người dùng bấm Cancel hoặc đóng hộp thoại
                System.exit(0);
            }

            try {
                int n = Integer.parseInt(input.trim());

                if (n <= 2) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Không có số nguyên tố nào nhỏ hơn " + n + ".\nTổng = 0",
                        "Kết quả",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    long sum = sumPrimesLessThanN(n);
                    JOptionPane.showMessageDialog(
                        null,
                        "Tổng các số nguyên tố nhỏ hơn " + n + " là: " + sum,
                        "Kết quả",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Vui lòng nhập vào một số nguyên hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
