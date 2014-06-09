package ru.spbau.komarov.repl;

import ru.spbau.komarov.repl.lexer.Lexer;
import ru.spbau.komarov.repl.lexer.Token;
import ru.spbau.komarov.repl.parser.AST.Assignment;
import ru.spbau.komarov.repl.parser.AST.Exp;
import ru.spbau.komarov.repl.parser.AST.Num;
import ru.spbau.komarov.repl.parser.Parser;
import ru.spbau.komarov.repl.visitors.AstToString;
import ru.spbau.komarov.repl.visitors.Evaluator;

import javax.swing.text.StyledDocument;
import java.util.*;


public class Manager {

    private Exp astRoot;

    private Lexer lexer = new Lexer();
    private Parser parser = new Parser();
    private Evaluator evaluator = new Evaluator();
    private AstToString astToString = new AstToString();

    private String errorMessage = "";
    private Stack<Map<String, Exp>> stackContexts = new Stack<>();
    private ArrayList<Token> tokens = new ArrayList<>();


    public Manager() {
        saveLastContext();
    }

    public void buildTree(String userInput) {
        clearAll();
        tokens = lexer.getTokens(userInput);
        try {
            astRoot = parser.getRootAST(tokens);
        } catch (SyntaxErrorException e) {
            astRoot = null;
            errorMessage = e.getMessage();
        }
    }

    public String getAnswer(boolean isSimplify) throws OutputErrorException {
        if (isSimplify) {
            return simplify();
        } else {
            return execute();
        }
    }

    public String simplify () throws OutputErrorException {
        if (astRoot != null ) {
            astRoot.accept(evaluator);
            evaluator.getAnswer().accept(astToString);
            String answer = astToString.getAnswer();
            astToString.clear();
            return answer;
        }  else {
            throw new OutputErrorException( errorMessage.isEmpty() ? "not command" : errorMessage);
        }
    }

    public String execute() throws OutputErrorException{
        if (astRoot != null ) {
            astRoot.accept(evaluator);
            Exp result = evaluator.getAnswer();

            if (result instanceof Num) {
                Double ans = ((Num) result).number.doubleValue();
                return ans.toString();
            } else if (result instanceof Assignment) {
                Assignment assign = (Assignment) result;
                if (assign.value instanceof Num) {
                    Double ans=((Num) assign.value).number.doubleValue();
                    return ans.toString();
                }
            }
        }
        errorMessage = evaluator.maybeErrorMessage;
        throw new OutputErrorException(errorMessage.isEmpty() ? "not command" : errorMessage);
    }



    public void clearAll() {
        evaluator.maybeErrorMessage = "";
        errorMessage = "";
        tokens.clear();
        astRoot = null;
    }

    public void saveLastContext() {
        Map<String, Exp> context = new HashMap<>();
        context.putAll(evaluator.getContext());
        stackContexts.add(context);
    }
    public void deleteLastContext() {
        stackContexts.pop();
        evaluator.setContext(stackContexts.peek());
    }



    public void colorize(StyledDocument document, int begin, String userInput,  boolean isSimplify) {

        if ( astRoot == null ) {
            document.setCharacterAttributes(begin, userInput.length(), document.getStyle("error"), false);
            return;
        }

        document.setCharacterAttributes(begin, userInput.length(), document.getStyle("ordinary"), false);

        for (Token t : tokens) {
            switch (t.type) {

                case VARIABLE :
                case NUMBER :
                    document.setCharacterAttributes(begin + t.offset, t.length, document.getStyle("value"), false);
                    break;

                case LEFT_BRACKET:
                case RIGHT_BRACKET:
                case SUB:
                case SUM:
                case MUL:
                case DIV:
                case ASSIGN:
                    document.setCharacterAttributes(begin + t.offset, t.length, document.getStyle("operator"), false);
                    break;

                default:
                    break;
            }
        }

        if(!isSimplify) {
            for (int i = 0; i < tokens.size(); i++) {
                Token t = tokens.get(i);
                if (t.type.equals(Token.Type.VARIABLE) && !stackContexts.peek().containsKey(t.name)) {
                    if ((i < tokens.size() - 1) && !tokens.get(i + 1).type.equals(Token.Type.ASSIGN))
                        document.setCharacterAttributes(begin + t.offset, t.length, document.getStyle("error"), false);
                }
            }
        }
    }
}
