import java.util.HashMap;
import java.util.Map;

// Interface Expression: Define o contrato para interpretar expressões.
interface Expression {
    boolean interpret(Context context);
}

// Terminal Expression: Representa uma variável na expressão.
class TerminalExpression implements Expression {
    private final String variable;

    public TerminalExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public boolean interpret(Context context) {
        return context.getVariable(variable);
    }
}

// Non-terminal Expression: Representa uma expressão composta por duas subexpressões.
class OrExpression implements Expression {
    private final Expression expr1;
    private final Expression expr2;

    public OrExpression(Expression expr1, Expression expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public boolean interpret(Context context) {
        return expr1.interpret(context) || expr2.interpret(context);
    }
}

// Contexto: Mantém o estado do contexto durante a interpretação.
class Context {
    private final Map<String, Boolean> variables;

    public Context() {
        this.variables = new HashMap<>();
    }

    public void setVariable(String variable, boolean value) {
        variables.put(variable, value);
    }

    public boolean getVariable(String variable) {
        return variables.getOrDefault(variable, false);
    }
}

// Cliente: Utiliza o padrão Interpreter para avaliar expressões lógicas.
public class InterpreterExample {
    public static void main(String[] args) {
        // Inicialização do contexto com variáveis.
        Context context = new Context();
        context.setVariable("A", true);
        context.setVariable("B", false);
        context.setVariable("C", true);

        // Construção da gramática: A OU (B E C)
        Expression expression = new OrExpression(
                new TerminalExpression("A"),
                new OrExpression(
                        new TerminalExpression("B"),
                        new TerminalExpression("C")
                )
        );

        // Avaliação da expressão com variáveis no contexto.
        boolean result = expression.interpret(context);
        System.out.println("Resultado da expressão: " + result);
    }
}
