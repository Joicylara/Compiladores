package codigo;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Delimitador extends UnaryOperator<List<String>> {
    static Delimitador splitBy(String limite){
        final String formato = "((?<=%1$s)|(?=%1$s))";
        final String formatoDelimitado = String.format(formato, limite);
        return linha -> linha.stream().map(str -> str.split(formatoDelimitado))
                .flatMap(Stream::of)
                .collect(Collectors.toList());
    }
    //irá encadear os metodos do programa
    default Delimitador andThen(Delimitador other){
        return linha -> {
            List<String> otherResult = other.apply(linha);
            return this.apply(otherResult);
        };
    }
}
