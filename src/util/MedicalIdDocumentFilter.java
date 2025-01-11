package util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MedicalIdDocumentFilter extends DocumentFilter {
    private final Pattern beginingDigitPattern = Pattern.compile("[1-9]");
    private final Pattern regularDigitPattern = Pattern.compile("[0-9]");
    private final Pattern multipleBeginingDigitsPattern = Pattern.compile("[1-9][0-9]*");
    private final Pattern multipleRegularDigitsPattern = Pattern.compile("[0-9]*");

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        Matcher matcher;
        if (offset == 0) {
            if (text.length() == 1) {
                matcher = beginingDigitPattern.matcher(text);
            } else {
                matcher = multipleBeginingDigitsPattern.matcher(text);
            }
        } else {
            if (text.length() == 1) {
                matcher = regularDigitPattern.matcher(text);
            } else {
                matcher = multipleRegularDigitsPattern.matcher(text);
            }
        }

        if(matcher.matches() && ((fb.getDocument().getLength() + text.length()) <= 9)) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
}
