package com.mycompany.cadastro_de_trecos.crud;

import static com.mycompany.cadastro_de_trecos.Cadastro_de_trecos.clearScreen;
import static com.mycompany.cadastro_de_trecos.Cadastro_de_trecos.exitProgram;
import static com.mycompany.cadastro_de_trecos.Cadastro_de_trecos.mainMenu;
import static com.mycompany.cadastro_de_trecos.Tools.showRes;
import static com.mycompany.cadastro_de_trecos.crud.Read.viewStatus;
import com.mycompany.cadastro_de_trecos.db.DbConnection;
import com.mycompany.cadastro_de_trecos.setup.AppSetup;
import java.sql.SQLException;
import java.util.Scanner;

public class Search extends AppSetup {

    /*No geral, esse trecho de código executa uma consulta de pesquisa no banco de dados 
      com base em um termo de pesquisa fornecido pelo usuário e exibe os resultados correspondentes, 
      ou exibe uma mensagem de erro se nenhum resultado for encontrado. */
    
    public static void search() {

        // Reserva recursos.
        String searchString;
        String sql;
        Scanner keyb = new Scanner(System.in, "latin1");

        // Cabeçalho da view.
        System.out.println(appName + "\n" + appSep);
        System.out.println("Pesquisa registros");
        System.out.println(appSep + "\n");

        // Recebe string de pesquisa.
        System.out.print("Digite o termo para buscar ou [0] para sair: ");
        searchString = keyb.nextLine().trim();
        if (searchString.equals("0")) {

            // Se digitou "0", sai para o menu principal.
            clearScreen();
            mainMenu();

        } else {

            // Se digitou uma string.
            try {

                sql = "SELECT *, DATE_FORMAT(data, '%d/%m/%Y às %H:%i') AS databr FROM " + DBTABLE + " WHERE nome LIKE ? OR descricao LIKE ?";
                conn = DbConnection.dbConnect();
                pstm = conn.prepareStatement(sql);
                /*Essas linhas atribuem os valores de pesquisa aos placeholders na consulta preparada. 
                  O valor da variável searchString é usado, com % adicionados antes e depois, 
                  para realizar uma correspondência parcial na pesquisa.
                  Após a execução da consulta, o resultado é armazenado na variável res. 
                  A próxima parte do código verifica se foram encontrados registros: */
                
                pstm.setString(1, "%" + searchString + "%");
                pstm.setString(2, "%" + searchString + "%");
                res = pstm.executeQuery();
                
                /*Se a consulta retornar algum resultado (pelo menos um registro), 
                  o código entra no bloco if e itera sobre os registros encontrados, 
                  chamando o método showRes() para exibi-los na visualização. */
                if (res.next()) {

                    // Se encontrou registros, exibe na view.
                    do {
                        showRes(res);
                    } while (res.next());

                } else {

                    // Se não tem registro.
                    /*Caso não sejam encontrados registros, o código entra no bloco else, realiza algumas ações, 
                      como limpar a tela e exibir uma mensagem de erro, e em seguida chama o método search() 
                      novamente para permitir uma nova pesquisa.*/
                    clearScreen();
                    System.out.println("Oooops! Não achei nada!\n");
                    search();
                }

                // Fecha recursos.
                DbConnection.dbClose(res, stmt, pstm, conn);

                // Menu inferior da seção.
                System.out.println(appSep);
                System.out.println("Menu:\n\t[1] Menu principal\n\t[2] Procurar outro\n\t[0] Sair");
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
                    case "2":
                        clearScreen();
                        search();
                        break;
                    default:
                        clearScreen();
                        System.out.println("Oooops! Opção inválida!\n");
                        search();
                }

            } catch (SQLException error) {

                // Tratamento de erros.
                System.out.println("Oooops! " + error.getMessage());
                System.exit(0);
            }

        }

    }

}
