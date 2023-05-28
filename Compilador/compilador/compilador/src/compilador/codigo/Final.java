package compilador.codigo;
import compilador.Lexico;
import compilador.SintaticException;

//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class Final {

    private final int lengthLine = 80;
    //private final String headerProgram;
    private final String init;
    private final String end;
    private final String tab = " ".repeat(4);
    private List<String> pseudoAsm;
    private String textSection;
    private String dataSection;
    private String bssSection;
    private Integer count = 0;

    public Final(){
        this(null);

    }

    public Final(List<String> pseudoAsm){

       /* var fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        var data = "; | Data : %s%s%s".formatted(
                fmt.format(LocalDateTime.now()),
                " ".repeat(22),
                "|"
        );*/
        this.pseudoAsm = pseudoAsm;
        /*this.headerProgram =  """
                """.formatted(data);*/
        this.bssSection =
                """
                        section .bss%s%s
                        """.formatted(" ".repeat(lengthLine - 12),
                        "; Inicio da seção de variáveis"
                );
        this.dataSection =
                """
                        section .data%s%s
                            fmtin: db "%s", 0x0%s%s
                            fmtout: db "%s", 0xA, 0x0%s%s
                        """.formatted(
                        " ".repeat(lengthLine - 13),
                        "; Inicio da seção de constantes",
                        "%d",
                        " ".repeat(lengthLine - 23),
                        "; Formatador de input",
                        "%d",
                        " ".repeat(lengthLine - 29),
                        "; Formatador de output"
                );
        this.textSection =
                """
                        section .text%s%s
                            global _start%s%s
                            extern write%s%s
                            extern read%s%s
                        _start:
                        """.formatted(" ".repeat(lengthLine - 13),
                        "; Inicio da seção do código",
                        " ".repeat(lengthLine - 15),
                        "; Declaração do start",
                        " ".repeat(lengthLine - 17),
                        "; Importação do write",
                        " ".repeat(lengthLine - 16),
                        "; Importação do read");
        this.init =
                """
                            ; Preparação da pilha
                            push ebp
                            mov ebp, esp
                        """;
        this.end =
                """
                            ; Término do programa
                            mov esp, ebp
                            pop ebp
                            ret
                        """;
    }

    public void setPseudoAsm(List<String> pseudoAsm) {
        this.pseudoAsm = pseudoAsm;
    }

    public String Construtor() throws FinalException {

        if (this.pseudoAsm == null) throw new FinalException("Codigo não foi inserido");

        var it = pseudoAsm.iterator();

        this.textSection += this.init;

        while (it.hasNext()) {
            var obj = it.next();
            if ("INT".equals(obj)) {
                createVar(it);
            } else if ("WRITE".equals(obj)) {
                createWrite(it);
            } else if ("READ".equals(obj)) {
                createRead(it);
            } else if ("IF".equals(obj)) {
                createIF(it);
            } else if ("GOTO".equals(obj)) {
                createGoto(it);
            } else if (obj.contains("_L")) {
                createLabel(obj);
            } else {
                createExpression(obj, it);
            }
        }

        this.bssSection += "\n";
        this.dataSection += "\n";
        this.textSection += this.end;

        return /*headerProgram + */ dataSection + bssSection + textSection;


    }

    private void createExpression(String object, Iterator<String> it) {
        var expr = object.split(" ");

        var first = convertToRegister(expr[0]);

        var second = convertToRegister(expr[2]);

        if (expr.length == 3) {
            this.textSection += tab + "mov eax, " + second + "\n";
            this.textSection += tab + "mov " + first + ", eax\n";
        } else {
            var third = convertToRegister(expr[3]);
            var opt = Optional.ofNullable(
                    switch (expr[4]) {
                        case "+" -> "add";
                        case "-" -> "sub";
                        case "*" -> "mul";
                        case "/" -> "div";
                        default -> null;
                    }
            );
            var operation = opt
                    .orElseThrow(() -> new SintaticException(
                            "Ill-formed expression " + Arrays.toString(expr)
                    ));
            this.textSection += tab + "mov eax, " + second + "\n";
            this.textSection += tab + "mov ebx, " + third + "\n";

            this.textSection += switch (operation) {
                case "mul" -> tab + operation + " ebx\n";
                case "div" -> """
                            xor edx, edx
                            %s ebx
                        """.formatted(operation);
                default -> tab + operation + " eax, ebx\n";
            };

            this.textSection += tab + "mov " + first + ", eax\n";
        }
    }

    private String convertToRegister(String object) {
        return switch (object) {
            case "_t0" -> "ecx";
            case "_t1" -> "edx";
            default -> {
                if (Lexico.isId(object)) {
                    yield "[" + object + "]";
                }
                yield object;
            }
        };
    }

    private void createLabel(String label) {
        this.textSection += label + "\n";

    }

    private void createGoto(Iterator<String> it) {
        this.textSection += tab + "jmp " + it.next() + "\n";
    }

    private void createIF(Iterator<String> it) {
        var args = parseCondition(it.next());

        it.next();
        var obj = it.next();

        BiFunction<String, String, String> toAsm = (str, label) -> {
            if (str.equals(">=")) {
                return "jge " + label;
            } else {
                return "jle " + label;
            }
        };

        var condition =
                """
                            mov eax, %s
                            cmp eax, %s
                            %s
                        """.formatted(
                        args.val1,
                        args.val2,
                        toAsm.apply(args.val3, obj)
                );

        textSection += condition;
    }
    
    public Objeto parseCondition(String condition){
        String[] val = condition.split(" ");

        
        UnaryOperator<String> identifier = str -> Lexico.isId(str)
                ? "["+ str +"]" : str;
        return new Objeto(identifier.apply(val[0]), val[1], val[2]);
    }


    private void createRead(Iterator<String> it) {
        var obj = it.next();
        var read =
                """
                            ; Ler a entrada do usuário para a variável %s
                            push %s
                            push dword fmtin
                            call scanf
                            add esp, 8
                        """.formatted(obj, obj);
        this.textSection += read;
    }

    private void createWrite(Iterator<String> it) {
        var obj = it.next();
        String write;
        if (Lexico.isId(obj)) {
            write = """
                        ; Escrever a variável %s na saída
                        push dword [%s]
                        push dword fmtout
                        call printf
                        add esp, 8
                    """.formatted(obj, obj);
        } else {
            var str = "str_" + (count);
            write = """
                        ; Escrever a string %s na saída
                        push dword %s
                        call printf
                        add esp, 4
                    """.formatted(str, str);
            var decl = "%s%s: db %s, 0xA, 0x0".formatted(tab, str, obj);
            var constDeclaration = "%s%s%s".formatted(decl, " ".repeat(lengthLine - decl.length()), "; Declaração da string\n");
            this.dataSection += constDeclaration;
            count++;
        }
        this.textSection += write;

    }

    private void createVar(Iterator<String> it) {
        var obj = it.next();
        var decl = "%s%s: resd 1".formatted(tab, obj);

        var space = lengthLine - decl.length();

        String var = "%s%s%s".formatted(decl,
                " ".repeat(space),
                "; Declaração da variável " + obj + "\n"
        );
        this.bssSection += var;
    }
}
