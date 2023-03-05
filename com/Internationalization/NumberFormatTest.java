package com_second.Internationalization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class NumberFormatTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new NumberFormatFrame();
            frame.setTitle("NumberFormatTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
class NumberFormatFrame extends JFrame{
    private Locale[] locales;
    private double currentNumber;
    private JComboBox<String> localeCombo = new JComboBox<>();
    private JButton parseButton = new JButton("Parse");
    private JTextField numberText = new JTextField(30);
    private JRadioButton numberRadioButton = new JRadioButton("Number");
    private JRadioButton currencyRadioButton= new JRadioButton("Currency");
    private JRadioButton percentRadioButton= new JRadioButton("Percent");
    private ButtonGroup rbGroup = new ButtonGroup();
    private NumberFormat currentNumberFormat;

    public NumberFormatFrame(){
        setLayout(new GridBagLayout());

        ActionListener listener = event -> updateDisplay();

        JPanel p = new JPanel();
        addRadioButton(p,numberRadioButton,rbGroup,listener);
        addRadioButton(p,currencyRadioButton,rbGroup,listener);
        addRadioButton(p,percentRadioButton,rbGroup,listener);

        add(new JLabel("Locale:"),new GBC(0,0).setAnchor(GBC.EAST));
        add(p,new GBC(1,1));
        add(parseButton, new GBC(0,2).setInsets(2));
        add(localeCombo, new GBC(1,0).setAnchor(GBC.WEST));
        add(numberText, new GBC(1,2).setFill(GBC.HORIZONTAL));
        locales = (Locale[]) NumberFormat.getAvailableLocales().clone();
        Arrays.sort(locales, Comparator.comparing(Locale::getDisplayName));
        for (Locale loc:locales)
            localeCombo.addItem(loc.getDisplayName());
        localeCombo.setSelectedItem(Locale.getDefault().getDisplayName());
        currentNumber = 123456.78;
        updateDisplay();

        localeCombo.addActionListener(listener);

        parseButton.addActionListener(event -> {
            String s = numberText.getText().trim();
            try{
                Number parse = currentNumberFormat.parse(s);
                currentNumber = parse.doubleValue();
                updateDisplay();
            } catch (ParseException e) {
                numberText.setText(e.getMessage());
            }
        });
        pack();


    }

    public void updateDisplay(){
        Locale locale = locales[localeCombo.getSelectedIndex()];
        currentNumberFormat = null;
        if (numberRadioButton.isSelected())
            currentNumberFormat = NumberFormat.getNumberInstance(locale);
        else if ((currencyRadioButton.isSelected())) {
            currentNumberFormat = NumberFormat.getCurrencyInstance(locale);
        } else if (percentRadioButton.isSelected()) {
            currentNumberFormat = NumberFormat.getPercentInstance(locale);
        }
        String formatted = currentNumberFormat.format(currentNumber);
        numberText.setText(formatted);
    }

    public void addRadioButton(Container p,JRadioButton b,ButtonGroup g,ActionListener listener){
        b.setSelected(g.getButtonCount() == 0);
        b.addActionListener(listener);
        g.add(b);
        p.add(b);
    }



}
