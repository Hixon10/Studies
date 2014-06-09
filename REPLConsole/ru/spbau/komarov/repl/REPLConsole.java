package ru.spbau.komarov.repl;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Stack;


public class REPLConsole {

    ////
    private JFrame frame;

    public static final String SIMPLIFY = "Simplify";
    public static final String EVALUATE = "Evaluate";
    public static final String GREETING = System.lineSeparator() + ">";

    private final StyledDocument document = new DefaultStyledDocument();
    private final JComboBox<String> optionPane = new JComboBox<>();
    ////

    private final Stack< Stack<UndoableEdit> > stackEditBlocks = new Stack<>();
    private final Stack<UndoableEdit> editBlockBuf = new Stack<>();

    private final Manager manager = new Manager();
    private final redactTextListener redactTextListener = new redactTextListener();
    private final ListenerForSaveEdits listenerForSaveEdits = new ListenerForSaveEdits();


    private void init() {
        ////
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setLayout(new BorderLayout());
        optionPane.addItem(SIMPLIFY);
        optionPane.addItem(EVALUATE);
        frame.add(optionPane, "North");
        ////

        Style style = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style ordinary = document.addStyle("ordinary", style);
        ordinary.addAttribute(StyleConstants.Underline, false);

        Style value = document.addStyle("value", style);
        StyleConstants.setBold(value, true);
        StyleConstants.setForeground(value, Color.BLUE);

        Style operator = document.addStyle("operator", style);
        StyleConstants.setForeground(operator, Color.GREEN);
        StyleConstants.setBold(operator, true);

        Style error = document.addStyle("error", style);
        StyleConstants.setBold(error, true);
        StyleConstants.setForeground(error, Color.RED);
        StyleConstants.setUnderline(error, true);

        ////
        JTextPane textArea = new JTextPane(document);
        ((AbstractDocument) document).setDocumentFilter(new Filter());
        textArea.setText("Welcome to REPL Console! " + System.lineSeparator() + ">");
        textArea.setEditable(true);
        frame.add(textArea, "Center");
        ////

        document.addUndoableEditListener(listenerForSaveEdits);
        document.addDocumentListener(redactTextListener);

        textArea.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke("ENTER"), new EnterAction());
        textArea.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke("control Z"), new UndoEditInBlockAction());
        textArea.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke("control shift Z"), new UndoExpressionAction());

        ////
        frame.setVisible(true);
        frame.setSize(500, 300);
        ////
    }


    private class ListenerForSaveEdits implements UndoableEditListener {
        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            UndoableEdit edit = e.getEdit();
            if (edit.canUndo()) {
                editBlockBuf.push(edit);
            }
        }
    }

    private class redactTextListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            redactText(e);
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            redactText(e);
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
        }

        private void redactText(final DocumentEvent e) {
            try {
                final String text = document.getText(0, document.getLength());
                final int begin = lastLineIndex(document) + GREETING.length();
                final String userInput = text.substring(begin);

                if (userInput.isEmpty()) {
                    manager.clearAll();
                    return;
                }

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        manager.buildTree(userInput);

                        document.removeUndoableEditListener(listenerForSaveEdits);
                        document.removeDocumentListener(redactTextListener);

                        manager.colorize(document, begin, userInput, SIMPLIFY.equals(optionPane.getSelectedItem()));

                        document.addUndoableEditListener(listenerForSaveEdits);
                        document.addDocumentListener(redactTextListener);
                    }
                });

            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    }



    private class EnterAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JTextPane source = (JTextPane) e.getSource();
                String text = document.getText(0, document.getLength());
                String userInput = text.substring(lastLineIndex(document) + GREETING.length());

                String answer;
                try {
                    answer = manager.getAnswer(SIMPLIFY.equals(optionPane.getSelectedItem()));
                } catch (OutputErrorException e1) {
                    answer = e1.getMessage();
                }

                manager.clearAll();

                document.removeDocumentListener(redactTextListener);
                document.insertString(document.getEndPosition().getOffset()-1, System.lineSeparator() + answer + GREETING, null);
                source.setCaretPosition(document.getEndPosition().getOffset()-1);
                document.addDocumentListener(redactTextListener);

                stackEditBlocks.push(new Stack<UndoableEdit>());
                stackEditBlocks.peek().addAll(editBlockBuf);
                editBlockBuf.clear();

                manager.saveLastContext();
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    }




    private class UndoEditInBlockAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!editBlockBuf.isEmpty()) {
                editBlockBuf.pop().undo();
            }
        }
    }

    private class UndoExpressionAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if ( !stackEditBlocks.isEmpty() ) {
                document.removeDocumentListener(redactTextListener);
                Stack<UndoableEdit> block = stackEditBlocks.pop();
                while(!block.isEmpty()) {
                    block.pop().undo();
                }
                manager.deleteLastContext();
                document.addDocumentListener(redactTextListener);
            }
        }
    }


    public static void main(String[] args) {
        REPLConsole replConsole = new REPLConsole();
        replConsole.init();
    }

    ////////////////////

    private class Filter extends DocumentFilter {


        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (cursorOnLastLine(offset, fb)) {
                super.insertString(fb, offset, string, attr);
            }
        }
        public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
            if (offset > lastLineIndex(fb.getDocument()) + 1) {
                super.remove(fb, offset, length);
            }
        }
        public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs)
                throws BadLocationException {
            if (cursorOnLastLine(offset, fb)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

    }

    private static boolean cursorOnLastLine(int offset, DocumentFilter.FilterBypass fb) {
        return cursorOnLastLine(offset, fb.getDocument());
    }

    private static boolean cursorOnLastLine(int offset, Document document) {
        int lastLineIndex = 0;
        try {
            lastLineIndex = lastLineIndex(document);
        } catch (BadLocationException e) {
            return false;
        }
        return offset > lastLineIndex;
    }

    private static int lastLineIndex(Document document) throws BadLocationException {
        return document.getText(0, document.getLength()).lastIndexOf(System.lineSeparator());
    }

    /////////////////
}