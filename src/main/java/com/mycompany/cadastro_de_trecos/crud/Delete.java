package com.mycompany.cadastro_de_trecos.crud; //Essa linha declara o pacote do arquivo, indicando a localização do arquivo dentro da estrutura de diretórios.

import static com.mycompany.cadastro_de_trecos.Cadastro_de_trecos.*; //Essa linha importa todos os membros estáticos da classe Cadastro_de_trecos, que está em um pacote diferente.
import static com.mycompany.cadastro_de_trecos.Tools.showRes;  //Essa linha importa o membro estático showRes da classe Tools do pacote com.mycompany.cadastro_de_trecos.
import com.mycompany.cadastro_de_trecos.db.DbConnection; //Essa linha importa a classe DbConnection do pacote com.mycompany.cadastro_de_trecos.db.
import com.mycompany.cadastro_de_trecos.setup.AppSetup; //Essa linha importa a classe AppSetup do pacote com.mycompany.cadastro_de_trecos.setup.
import java.sql.SQLException;  //Essa linha importa a exceção SQLException do pacote java.sql.

//Essa linha declara a classe Delete que estende a classe AppSetup.
public class Delete extends AppSetup {

    //Essa linha declara o método estático delete().
    public static void delete() {

        // Reserva recursos para o banco de dados.
        int id = 0;
        String sql;

        // Cabeçalho da seção.
        System.out.println(appName + "\n" + appSep);
        System.out.println("Apaga um registro");
        System.out.println(appSep);
         
        //Essa linha inicia um bloco try-catch para tratar exceções.
        try {

            // Recebe o Id do teclado. 
            /*Essas linhas solicitam a entrada do usuário para digitar um ID. Se o ID for igual a 0, 
            o método clearScreen() é chamado e o método mainMenu() é chamado.*/
            System.out.print("Digite o ID ou [0] para retornar: ");
            id = Integer.parseInt(scanner.next());
            if (id == 0) {
                clearScreen();
                mainMenu();
            }
        } catch (NumberFormatException e) {

            // Quando opção é inválida.
            /*Essas linhas tratam a exceção NumberFormatException, caso a entrada do usuário não seja um número válido. 
             O método clearScreen() é chamado, uma mensagem de erro é exibida e o método delete() é chamado recursivamente. */
            clearScreen();
            System.out.println("Oooops! Opção inválida!\n");
            delete();
        }

        try {

            // Verifica se o registro existe.
            /*  Essas linhas realizam uma consulta SQL para verificar se existe um registro com o ID fornecido. 
                A variável sql contém a consulta que seleciona os campos e formata a data. A conexão com o banco de dados 
                é estabelecida através do método dbConnect() da classe DbConnection, e a consulta é preparada e executada. 
                O valor do ID é definido no PreparedStatement utilizando pstm.setInt(1, id). O resultado da consulta é armazenado na variável res.*/
            sql = "SELECT *, DATE_FORMAT(data, '%d/%m/%Y às %H:%i') AS databr FROM " + DBTABLE + " WHERE status = '2' and id = ?";
            conn = DbConnection.dbConnect();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            res = pstm.executeQuery();
          
            //Verifica se há pelo menos um resultado retornado pela consulta.
            if (res.next()) {

                // Se tem registro, chama o método showRes e exibe o resultado na view.
                showRes(res);

                System.out.print("Tem certeza que deseja apagar o registro? [s/N] ");
                if (scanner.next().trim().toLowerCase().equals("s")) {

                    sql = "UPDATE " + DBTABLE + " SET status = '0' WHERE id = ?";
                    pstm = conn.prepareStatement(sql);
                    pstm.setInt(1, id);
                    if (pstm.executeUpdate() == 1) {
                        // Registro apagado.
                        System.out.println("\nRegistro apagado!");
                    } else {
                        System.out.println("Oooops! Algo deu errado!");
                    }
                } else {
                    System.out.println("\nNada aconteceu!");
                }

            } else {
                clearScreen();
                System.out.println("Oooops! Não achei nada!\n");
                delete();
            }

            // Fecha banco de dados.
            DbConnection.dbClose(res, stmt, pstm, conn);

            // Menu inferior da seção.
            System.out.println(appSep);
            System.out.println("Menu:\n\t[1] Menu principal\n\t[2] Apagar outro\n\t[0] Sair");
            System.out.println(appSep);

            // Recebe opção do teclado.            
            System.out.print("Opção: ");
            String option = scanner.next();

            // Executa conforme a opção.
            switch (option) {
                case "0" ->
                    exitProgram();
                case "1" -> {
                    clearScreen();
                    mainMenu();
                }
                case "2" -> {
                    clearScreen();
                    delete();
                }
                default -> {
                    clearScreen();
                    System.out.println("Oooops! Opção inválida!\n");
                    delete();
                }
            }

        } catch (SQLException error) {

            // Tratamento de erros.
            System.out.println("Oooops! " + error.getMessage());
            System.exit(0);
        }

    }

}
