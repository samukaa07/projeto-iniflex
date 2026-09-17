import main.java.model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat NUMBER_FORMATTER = NumberFormat.getInstance(new Locale("pt", "BR"));

    static {
        NUMBER_FORMATTER.setMinimumFractionDigits(2);
        NUMBER_FORMATTER.setMaximumFractionDigits(2);
    }

    public static void main(String[] args) {

        // 3.1 - Inserir todos os funcionários na mesma ordem da tabela
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 - Remover o funcionário "João" da lista
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        // 3.3 - Imprimir todos os funcionários com as informações formatadas
        System.out.println("=== 3.3 - LISTA DE FUNCIONÁRIOS ===");
        imprimirFuncionarios(funcionarios);

        // 3.4 - Aumento de 10% no salário de todos
        funcionarios.forEach(f -> {
            BigDecimal novoSalario = f.getSalario()
                    .multiply(new BigDecimal("1.10"))
                    .setScale(2, RoundingMode.HALF_UP);
            f.setSalario(novoSalario);
        });

        // 3.5 - Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 - Imprimir os funcionários agrupados por função
        System.out.println("\n=== 3.6 - FUNCIONÁRIOS AGRUPADOS POR FUNÇÃO ===");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            lista.forEach(f -> System.out.println(" - " + f.getNome()));
        });

        // 3.8 - Imprimir funcionários com aniversário nos meses 10 e 12
        System.out.println("\n=== 3.8 - ANIVERSARIANTES DOS MESES 10 E 12 ===");
        funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .forEach(f -> System.out.println(f.getNome() + " - " + f.getDataNascimento().format(DATE_FORMATTER)));

        // 3.9 - Imprimir o funcionário com a maior idade
        System.out.println("\n=== 3.9 - FUNCIONÁRIO COM A MAIOR IDADE ===");
        funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .ifPresent(f -> {
                    int idade = Period.between(f.getDataNascimento(), LocalDate.now()).getYears();
                    System.out.println("Nome: " + f.getNome() + " | Idade: " + idade + " anos");
                });

        // 3.10 - Imprimir a lista por ordem alfabética
        System.out.println("\n=== 3.10 - FUNCIONÁRIOS EM ORDEM ALFABÉTICA ===");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> System.out.println(f.getNome()));

        // 3.11 - Imprimir o total dos salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\n=== 3.11 - TOTAL DOS SALÁRIOS ===");
        System.out.println("R$ " + NUMBER_FORMATTER.format(totalSalarios));

        // 3.12 - Imprimir quantos salários mínimos ganha cada funcionário (salário mínimo = R$ 1212.00)
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\n=== 3.12 - QTD. DE SALÁRIOS MÍNIMOS POR FUNCIONÁRIO ===");
        funcionarios.forEach(f -> {
            BigDecimal qtdSalarios = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha " + NUMBER_FORMATTER.format(qtdSalarios) + " salários mínimos.");
        });
    }

    private static void imprimirFuncionarios(List<Funcionario> lista) {
        for (Funcionario f : lista) {
            String dataFmt = f.getDataNascimento().format(DATE_FORMATTER);
            String salarioFmt = NUMBER_FORMATTER.format(f.getSalario());
            System.out.println(String.format("Nome: %-8s | Data Nasc: %s | Salário: R$ %-10s | Função: %s",
                    f.getNome(), dataFmt, salarioFmt, f.getFuncao()));
        }
    }
}