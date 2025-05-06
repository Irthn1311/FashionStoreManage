package components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerAutocomplete extends JTextField {
    private JWindow suggestionWindow;
    private JList<String> suggestionList;
    private DefaultListModel<String> listModel;
    private CustomerAutocomplete linkedField;
    private boolean isIdField;
    private static final int MAX_SUGGESTIONS = 5;

    public CustomerAutocomplete(boolean isIdField) {
        this.isIdField = isIdField;
        setupAutocomplete();
    }

    public void setLinkedField(CustomerAutocomplete field) {
        this.linkedField = field;
    }

    private void setupAutocomplete() {
        // Create suggestion window
        suggestionWindow = new JWindow();
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        scrollPane.setPreferredSize(new Dimension(getWidth(), 100));
        suggestionWindow.getContentPane().add(scrollPane);

        // Add document listener for text changes
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }
        });

        // Add key listener for navigation
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !suggestionList.isSelectionEmpty()) {
                    selectSuggestion();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    suggestionWindow.setVisible(false);
                }
            }
        });

        // Add mouse listener for selection
        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selectSuggestion();
                }
            }
        });
    }

    private void updateSuggestions() {
        String input = getText().trim();
        if (input.isEmpty()) {
            suggestionWindow.setVisible(false);
            return;
        }

        List<String> suggestions = getCustomerSuggestions(input);
        listModel.clear();
        
        for (String suggestion : suggestions) {
            listModel.addElement(suggestion);
        }

        if (!suggestions.isEmpty()) {
            showSuggestions();
        } else {
            suggestionWindow.setVisible(false);
        }
    }

    private List<String> getCustomerSuggestions(String input) {
        List<String> suggestions = new ArrayList<>();
        try {
            Connection conn = getConnection();
            String query = isIdField ? 
                "SELECT MaKhachHang, HoTen FROM KhachHang WHERE MaKhachHang LIKE ? LIMIT ?" :
                "SELECT MaKhachHang, HoTen FROM KhachHang WHERE HoTen LIKE ? LIMIT ?";
            
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, input + "%");
            pstmt.setInt(2, MAX_SUGGESTIONS);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("MaKhachHang");
                String name = rs.getString("HoTen");
                suggestions.add(id + " - " + name);
            }
            
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suggestions;
    }

    private void showSuggestions() {
        if (suggestionList.getModel().getSize() > 0) {
            Point location = getLocationOnScreen();
            suggestionWindow.setLocation(location.x, location.y + getHeight());
            suggestionWindow.setSize(getWidth(), 100);
            suggestionWindow.setVisible(true);
        }
    }

    private void selectSuggestion() {
        String selected = suggestionList.getSelectedValue();
        if (selected != null) {
            String[] parts = selected.split(" - ");
            if (parts.length == 2) {
                if (isIdField) {
                    setText(parts[0]);
                    if (linkedField != null) {
                        linkedField.setText(parts[1]);
                    }
                } else {
                    setText(parts[1]);
                    if (linkedField != null) {
                        linkedField.setText(parts[0]);
                    }
                }
            }
            suggestionWindow.setVisible(false);
        }
    }

    private Connection getConnection() throws SQLException {
        // Replace with your actual database connection details
        return DriverManager.getConnection(
            "jdbc:sqlserver://localhost:1433;databaseName=FashionStoreManage;encrypt=true;trustServerCertificate=true",
            "sa",
            "12345678"
        );
    }
} 