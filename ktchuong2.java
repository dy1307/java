import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ktchuong2 extends JFrame {
    // Các components nhập liệu
    private JTextField txtHoTen;
    private JTextField txtNgaySinh;
    private JTextField txtTenHP;
    private JTextField txtDiemCC;
    private JTextField txtDiemGK;
    private JTextField txtDiemCK;
    private JTextField txtSoHocPhan;

    private JButton btnNhapSoHP;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnNhapMoi;

    // Bảng dữ liệu JTable
    private JTable table;
    private DefaultTableModel tableModel;

    private int soHocPhanCanNhap = 0;
    private int soHocPhanDaNhap = 0;
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public ktchuong2() {
        setTitle("Quản Lý Điểm Học Phần - Kiểm Tra Chương 2");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Tiêu đề phía trên
        JLabel lblTitle = new JLabel("CHƯƠNG TRÌNH QUẢN LÝ ĐIỂM HỌC PHẦN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(25, 118, 210));
        lblTitle.setBorder(new EmptyBorder(15, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        // 2. Panel trung tâm chứa Form nhập liệu và Bảng
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBorder(new EmptyBorder(0, 15, 15, 15));

        // --- Form nhập liệu ---
        JPanel pnlInputContainer = new JPanel(new BorderLayout(10, 10));
        
        // Panel nhập n số học phần
        JPanel pnlSoHP = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlSoHP.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Cấu hình số học phần", 
                TitledBorder.LEFT, 
                TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 13), 
                new Color(33, 33, 33)
        ));
        pnlSoHP.add(new JLabel("Số học phần cần nhập (n):"));
        txtSoHocPhan = new JTextField(8);
        pnlSoHP.add(txtSoHocPhan);
        btnNhapSoHP = new JButton("Xác nhận n");
        btnNhapSoHP.setBackground(new Color(33, 150, 243));
        btnNhapSoHP.setForeground(Color.WHITE);
        btnNhapSoHP.setFocusPainted(false);
        pnlSoHP.add(btnNhapSoHP);
        
        JLabel lblStatus = new JLabel("(Chưa giới hạn n)");
        lblStatus.setForeground(Color.GRAY);
        pnlSoHP.add(lblStatus);

        pnlInputContainer.add(pnlSoHP, BorderLayout.NORTH);

        // Panel chi tiết thông tin
        JPanel pnlFields = new JPanel(new GridLayout(3, 4, 10, 10));
        pnlFields.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Thông tin sinh viên & Học phần", 
                TitledBorder.LEFT, 
                TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 13), 
                new Color(33, 33, 33)
        ));

        pnlFields.add(new JLabel("Họ và tên:"));
        txtHoTen = new JTextField();
        pnlFields.add(txtHoTen);

        pnlFields.add(new JLabel("Điểm chuyên cần (CC - 10%):"));
        txtDiemCC = new JTextField();
        pnlFields.add(txtDiemCC);

        pnlFields.add(new JLabel("Ngày sinh (dd/mm/yyyy):"));
        txtNgaySinh = new JTextField();
        pnlFields.add(txtNgaySinh);

        pnlFields.add(new JLabel("Điểm giữa kỳ (GK - 20%):"));
        txtDiemGK = new JTextField();
        pnlFields.add(txtDiemGK);

        pnlFields.add(new JLabel("Tên học phần:"));
        txtTenHP = new JTextField();
        pnlFields.add(txtTenHP);

        pnlFields.add(new JLabel("Điểm cuối kỳ (CK - 70%):"));
        txtDiemCK = new JTextField();
        pnlFields.add(txtDiemCK);

        pnlInputContainer.add(pnlFields, BorderLayout.CENTER);

        // Panel các nút thao tác
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnThem = new JButton("Thêm Học Phần");
        btnThem.setBackground(new Color(46, 125, 50));
        btnThem.setForeground(Color.WHITE);
        btnThem.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnThem.setFocusPainted(false);

        btnNhapMoi = new JButton("Làm Mới Form");
        btnNhapMoi.setBackground(new Color(245, 124, 0));
        btnNhapMoi.setForeground(Color.WHITE);
        btnNhapMoi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNhapMoi.setFocusPainted(false);

        btnXoa = new JButton("Xóa Dòng Chọn");
        btnXoa.setBackground(new Color(211, 47, 47));
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnXoa.setFocusPainted(false);

        pnlButtons.add(btnThem);
        pnlButtons.add(btnNhapMoi);
        pnlButtons.add(btnXoa);

        pnlInputContainer.add(pnlButtons, BorderLayout.SOUTH);
        pnlCenter.add(pnlInputContainer, BorderLayout.NORTH);

        // --- Bảng hiển thị JTable ---
        String[] columnNames = {
            "STT", "Họ và tên", "Ngày sinh", "Tên học phần", 
            "Điểm CC", "Điểm GK", "Điểm CK", "Tổng kết", "Xếp loại"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(238, 238, 238));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Canh giữa cho các cột số/ngày
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < columnNames.length; i++) {
            if (i != 1 && i != 3) { // Trừ Họ tên và Tên học phần
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), 
                "Danh sách học phần & Kết quả", 
                TitledBorder.LEFT, 
                TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 13), 
                new Color(33, 33, 33)
        ));
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        // 3. Xử lý sự kiện
        // Sự kiện: Xác nhận số học phần n
        btnNhapSoHP.addActionListener(e -> {
            String strN = txtSoHocPhan.getText().trim();
            if (strN.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số học phần n!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int n = Integer.parseInt(strN);
                if (n <= 0) {
                    JOptionPane.showMessageDialog(this, "Số học phần phải > 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                soHocPhanCanNhap = n;
                soHocPhanDaNhap = 0;
                tableModel.setRowCount(0);
                lblStatus.setText("(Cần nhập " + soHocPhanCanNhap + " học phần, đã nhập: 0)");
                lblStatus.setForeground(new Color(25, 118, 210));
                JOptionPane.showMessageDialog(this, "Đã thiết lập cần nhập " + n + " học phần. Hãy nhập thông tin từng học phần!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số học phần n phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sự kiện: Thêm học phần
        btnThem.addActionListener(e -> themHocPhan(lblStatus));

        // Sự kiện: Làm mới form
        btnNhapMoi.addActionListener(e -> xoaTrangForm());

        // Sự kiện: Xóa dòng được chọn
        btnXoa.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa trong bảng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa dòng này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(selectedRow);
                // Đánh lại số thứ tự
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    tableModel.setValueAt(i + 1, i, 0);
                }
                if (soHocPhanCanNhap > 0 && soHocPhanDaNhap > 0) {
                    soHocPhanDaNhap--;
                    lblStatus.setText("(Cần nhập " + soHocPhanCanNhap + " học phần, đã nhập: " + soHocPhanDaNhap + ")");
                }
            }
        });
    }

    private void themHocPhan(JLabel lblStatus) {
        if (soHocPhanCanNhap > 0 && soHocPhanDaNhap >= soHocPhanCanNhap) {
            JOptionPane.showMessageDialog(this, "Đã nhập đủ số lượng " + soHocPhanCanNhap + " học phần theo yêu cầu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String hoTen = txtHoTen.getText().trim();
        String ngaySinh = txtNgaySinh.getText().trim();
        String tenHP = txtTenHP.getText().trim();
        String strCC = txtDiemCC.getText().trim();
        String strGK = txtDiemGK.getText().trim();
        String strCK = txtDiemCK.getText().trim();

        // Kiểm tra rỗng
        if (hoTen.isEmpty() || ngaySinh.isEmpty() || tenHP.isEmpty() || strCC.isEmpty() || strGK.isEmpty() || strCK.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ các trường thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra và parse điểm
        double cc, gk, ck;
        try {
            cc = Double.parseDouble(strCC);
            gk = Double.parseDouble(strGK);
            ck = Double.parseDouble(strCK);

            if (cc < 0 || cc > 10 || gk < 0 || gk > 10 || ck < 0 || ck > 10) {
                JOptionPane.showMessageDialog(this, "Điểm phải nằm trong thang điểm từ 0 đến 10!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Điểm CC, GK, CK phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Tính điểm học phần (Tổng kết = CC * 10% + GK * 20% + CK * 70%)
        double tongKet = cc * 0.1 + gk * 0.2 + ck * 0.7;

        // Tính xếp loại
        String xepLoai;
        if (tongKet >= 8.5) {
            xepLoai = "Giỏi";
        } else if (tongKet >= 7.0) {
            xepLoai = "Khá";
        } else if (tongKet >= 5.5) {
            xepLoai = "Trung bình";
        } else if (tongKet >= 4.0) {
            xepLoai = "Yếu";
        } else {
            xepLoai = "Kém";
        }

        // 3. Thêm vào JTable
        int stt = tableModel.getRowCount() + 1;
        Vector<Object> row = new Vector<>();
        row.add(stt);
        row.add(hoTen);
        row.add(ngaySinh);
        row.add(tenHP);
        row.add(df.format(cc));
        row.add(df.format(gk));
        row.add(df.format(ck));
        row.add(df.format(tongKet));
        row.add(xepLoai);

        tableModel.addRow(row);

        soHocPhanDaNhap++;
        if (soHocPhanCanNhap > 0) {
            lblStatus.setText("(Cần nhập " + soHocPhanCanNhap + " học phần, đã nhập: " + soHocPhanDaNhap + ")");
            if (soHocPhanDaNhap == soHocPhanCanNhap) {
                JOptionPane.showMessageDialog(this, "Đã nhập đủ " + soHocPhanCanNhap + " học phần!", "Hoàn tất", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        // Giữ lại Họ tên, Ngày sinh; xóa các ô học phần và điểm để nhập môn tiếp theo
        txtTenHP.setText("");
        txtDiemCC.setText("");
        txtDiemGK.setText("");
        txtDiemCK.setText("");
        txtTenHP.requestFocus();
    }

    private void xoaTrangForm() {
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtTenHP.setText("");
        txtDiemCC.setText("");
        txtDiemGK.setText("");
        txtDiemCK.setText("");
        txtHoTen.requestFocus();
    }

    public static void main(String[] args) {
        // Áp dụng giao diện hệ thống cho đẹp mắt
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            ktchuong2 frame = new ktchuong2();
            frame.setVisible(true);
        });
    }
}
