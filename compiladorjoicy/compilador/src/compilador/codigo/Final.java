package compilador.codigo;


//import java.util.Arrays;
import java.util.List;

import compilador.Lexico;
//import compilador.SintaticException;
//import compilador.Tokens;

//import org.javatuples.Triplet;

/*import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;*/

public class Final {

        public StringBuffer codigo;
        public StringBuffer textSection;
        public StringBuffer dataSection;
        public StringBuffer bssSection;
    
        public Final() {
            this.codigo = new StringBuffer();
            this.dataSection = new StringBuffer();
            this.textSection = new StringBuffer();
            this.bssSection = new StringBuffer();
    
            this.bssSection.append("\n section .bss\n ");
    
            this.dataSection.append(" section .data\n");
            this.dataSection.append("\tfmtin:\tdb \"%d\",  0x0\n ");
            this.dataSection.append("\tfmtout:\tdb \"%d\", 0xA, 0x0\n");
    
            this.textSection.append("\n section .text\n");
            this.textSection.append("\tglobal _main\n\textern _printf\n\textern _scanf\n");
            this.textSection.append("\n_main:\n ");
    
        }
    
        public String codFinal(List<String> intermediario) {
    
            int strCount = 1;
    
            this.textSection.append("\n  ; Preparação da pilha \n\tpush ebp\n \tmov ebp,esp\n");
    
            for (int i = 0; i < intermediario.size(); i++) {
    
                if (intermediario.get(i).equals("WRITE")) {
    
                    var str = intermediario.get(++i);
                    var write = "";
                    if (Lexico.isId(str)) {
                        write += "\n; Escrever a variável na saída\n"+
                                "\tpush dword [" + str + "]\n" +
                                "\tpush dword fmtout\n" +
                                "\tcall _printf\n" +
                                "\tadd esp, 8\n";
                    } else {
                        var constStr = "str_" + (strCount++);
                        write +="\n; Escrever a string na saída\n"+ 
                                "\tpush dword " + constStr + "\n" +
                                "\tcall _printf\n" +
                                "\tadd esp, 4\n";
                        var declaracao = "\t" + constStr + ": db " + str + ", 10,0\n";
                        dataSection.append(declaracao);
    
                    }
                    textSection.append(write);
    
                } else if (intermediario.get(i).equals("INT")) {
                    bssSection.append("\t").append(intermediario.get(++i)).append(": resd 1\n");
    
                } else if (intermediario.get(i).equals("READ")) {
                    textSection.append("\n ; Ler a entrada do usuário para a variável\n");
                    textSection.append("\tpush ").append(intermediario.get(++i));
                    textSection.append("\n\tpush dword fmtin\n");
                    textSection.append("\tcall _scanf\n");
                    textSection.append("\tadd esp, 8\n");
    
                } else if (intermediario.get(i).equals("IF")) {
    
    
                    var exp = intermediario.get(++i).split(" ");
    
                    if (Lexico.isId(exp[0])) {
                        exp[0] = "[" + exp[0] + "]";
                    }
                    if (Lexico.isId(exp[2])) {
                        exp[2] = "[" + exp[2] + "]";
                    }
                    i += 2;
                    var label = intermediario.get(i);
                    var jmp = "";
                    if (exp[1].equals(">=")) {
                        jmp = "\tjge " + label + "; maior ou igual"+ "\n";
                    } else if (exp[1].equals("<=")){
                        jmp = "\tjle " + label + "; menor ou igual"+ "\n";
                    }else if(exp[1].equals("==")){
                        jmp = "\tjne " + label + "; salta se nao igual"+ "\n";
                    }else{
                        jmp = "\tje " + label + "; salta se igual"+ "\n";
                    }

                    textSection.append("\n\tmov eax, " + exp[0] + "\n");
                    textSection.append("\tmov ebx, " + exp[2] + "\n");
                    textSection.append("\tcmp eax, ebx"+ "\n");
                    textSection.append(jmp);
    
                } else if (intermediario.get(i).contains("_L") && !textSection.toString().contains(intermediario.get(i))) {
                    textSection.append(intermediario.get(i) + "\n");
    
                } else if (intermediario.get(i).contains("GOTO")) {
                    textSection.append("\n\t;Salta para a instrução do rótulo passado" + "\n");
                    textSection.append("\n\tjmp " + intermediario.get(++i) + "\n");
    
                } else {
                    var expr = intermediario.get(i).split(" ");
                   
                    var first = toRegister(expr[0]);
                    var second = toRegister(expr[2]);
    
    
                    if (expr.length == 3) {
                        textSection.append("\n\tmov eax," + second + "\n");
                        textSection.append("\tmov " + first + ", eax\n");
                    } else {
                        var third = toRegister(expr[3]);
                        var opr = switch (expr[4]) {
                            case "+" -> "add";
                            case "-" -> "sub";
                            case "*" -> "mul";
                            case "/" -> "div";
                            default -> " ";
                        };
                        textSection.append("\n\tmov eax, " + second + "\n");
                        textSection.append("\tmov ebx, " + third + "\n");
    
                        switch (opr) {
                            /*case "add":
                                textSection.append("\t" + opr + " eax, ebx\n");
                                break;
                            case "sub":
                                textSection.append("\t" + opr + " ebx, ecx\n");  
                                break; */ 
                            case "mul":
                                textSection.append("\t" + opr + " ebx\n");
                                break;
                            case "div":
                                textSection.append("\txor edx, edx\n");
                                textSection.append("\t").append(opr).append(" ebx\n");
                                break;
                            default:
                                textSection.append("\t" + opr + " eax, ebx\n");
                                break;
                        }
                        textSection.append("\tmov " + first + ", eax\n");
                    }
                }
            }
    
            codigo.append(dataSection);
            codigo.append(bssSection);
            codigo.append(textSection);
            codigo.append(";final do programa");
            codigo.append("\n\tmov esp,ebp\n \tpop ebp\n \tret");
            return codigo.toString();
        }
    
        private String toRegister(String strb) {
            switch (strb) {
                case "_t0":
                    return "ecx";
                case "_t1":
                    return "edx";
                default:
                    if (Lexico.isId(strb)) {
                        return "[" + strb + "]";
                    }
                    return strb;
            }
        }
    






} 