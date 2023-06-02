package com.mycompany.cadastro_de_trecos.crud;

import com.mycompany.cadastro_de_trecos.setup.AppSetup;
import com.mycompany.cadastro_de_trecos.db.DbConnection;
import static com.mycompany.cadastro_de_trecos.Cadastro_de_trecos.clearScreen;
import static com.mycompany.cadastro_de_trecos.Cadastro_de_trecos.exitProgram;
import static com.mycompany.cadastro_de_trecos.Cadastro_de_trecos.mainMenu;
import static com.mycompany.cadastro_de_trecos.Tools.showRes;
import java.sql.SQLException;

public class Read extends AppSetup {

    static String sql = "";
    static String viewStatus;

    // Lista todos os registros.
    public static void readAll() {

        // Reserva recursos.
        // Cabeçalho da view.
        System.out.println(appName + "\n" + appSep);
        System.out.println("Lista todos os registros");
        System.out.println(appSep + "\n");

        try {

            // Consulta o banco de dados.
            sql = "SELECT *, DATE_FORMAT(data, '%d/%m/%Y às %H:%i') AS databr FROM " + DBTABLE + " WHERE status != '0' ORDER BY nome ASC;";
            conn = DbConnection.dbConnect();
            stmt = conn.createStatement();
            res = stmt.executeQuery(sql);
            if (res.next()) {

                // Se encontrou registros.
                do {
                showRes(res);
                } while (res.next());
            } else {

                // Se não encontrou registro.
                clearScreen();
                System.out.println("Oooops! Não achei nada!\n");
            }

            // Fecha recursos.
            DbConnection.dbClose(res, stmt, pstm, conn);

            // Menu inferior da seção.
            System.out.println(appSep);
            System.out.println("Menu:\n\t[1] Menu principal\n\t[0] Sair");
            System.out.println(appSep);

            // Recebe opção do teclaso.
            System.out.print("Opção: ");
            String option = scanner.next();

            // Executa conforme a opção.
            switch (option) {
                case "0":
                    exitProgram();
                    break;
                case "1":
                    clearScreen();
                    mainMenu();
                    break;
                default:
                    clearScreen();
                    System.out.println("Oooops! Opção inválida!\n");
                    readAll();
            }

        } catch (SQLException error) {

            // Tratamento de erros.
            System.out.println("Oooops! " + error.getMessage());
            System.exit(0);
        }

    }

    // Lista um único registro pelo id.
    public static void read() {

        // Reserva recursos para o banco de dados.
        int id = 0;
        

        // Cabeçalho da seção.
        System.out.println(appName + "\n" + appSep);
        System.out.println("Lista um registro");
        System.out.println(appSep);

        try {

            // Recebe o Id do teclado.
            System.out.print("Digite o ID ou [0] para retornar: ");
            id = Integer.parseInt(scanner.next());
            if (id == 0) {
                clearScreen();
                mainMenu();
            }
        } catch (NumberFormatException e) {

            // Quando opção é inválida.
            clearScreen();
            System.out.println("Oooops! Opção inválida!\n");
            read();
        }

        try {

            // Faz consulta no banco de dados usando "preparedStatement".
            /*SELECT * - Seleciona todos os campos da tabela.
              DATE_FORMAT(data, '%d/%m/%Y às %H:%i') AS databr - Formata o campo data
              no formato "dia/mês/ano às hora:minuto" e atribui o alias(apelido) databr ao resultado formatado. 
              Isso permite que a data seja exibida em um formato mais legível.
              FROM [DBTABLE] - Especifica a tabela a ser consultada. O nome da tabela é obtido de uma variável chamada DBTABLE,
              que contém o nome da tabela.
              WHERE status != '0' and id = ? - Define as condições para filtrar os registros. A condição status != '0' 
              significa que o campo status não deve ser igual a '0'. Isso é usado para excluir registros com status '0'.
              A condição id = ? é um placeholder para o valor do ID que será definido posteriormente no PreparedStatement.
              Essa consulta é usada para obter registros da tabela onde o status não é igual a '0' 
              e o ID corresponde a um valor específico fornecido posteriormente. O valor do ID é definido usando um PreparedStatement,
              que é uma técnica segura para evitar ataques de injeção de SQL.*/
            sql = "SELECT *, DATE_FORMAT(data, '%d/%m/%Y às %H:%i') AS databr FROM " + DBTABLE + " WHERE status != '0' and id = ?";
            conn = DbConnection.dbConnect();
            pstm = conn.prepareStatement(sql);

            // Passa o Id para a query.
            pstm.setInt(1, id);

            // Executa a query.
            res = pstm.executeQuery();

            if (res.next()) {

               showRes(res);
            } else {

                // Se não tem registro.
                clearScreen();
                System.out.println("Oooops! Não achei nada!\n");
                read();
            }

            // Fecha banco de dados.
            DbConnection.dbClose(res, stmt, pstm, conn);

            // Menu inferior da seção.
            System.out.println(appSep);
            System.out.println("Menu:\n\t[1] Menu principal\n\t[2] Listar outro\n\t[0] Sair");
            System.out.println(appSep);

            // Recebe opção do teclado.            
            System.out.print("Opção: ");
            String option = scanner.next();

            // Executa conforme a opção.
            switch (option) {
                case "0":
                    exitProgram();
                    break;
                case "1":
                    clearScreen();
                    mainMenu();
                    break;
                case "2":
                    clearScreen();
                    read();
                    break;
                default:
                    clearScreen();
                    System.out.println("Oooops! Opção inválida!\n");
                    read();
            }

        } catch (SQLException error) {

            // Tratamento de erros.
            System.out.println("Oooops! " + error.getMessage());
            System.exit(0);
        }

    }

}
