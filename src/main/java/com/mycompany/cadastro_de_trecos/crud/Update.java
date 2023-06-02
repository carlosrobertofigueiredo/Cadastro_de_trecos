package com.mycompany.cadastro_de_trecos.crud;

import static com.mycompany.cadastro_de_trecos.Cadastro_de_trecos.*;
import static com.mycompany.cadastro_de_trecos.Tools.showRes;
import static com.mycompany.cadastro_de_trecos.crud.Read.read;
import com.mycompany.cadastro_de_trecos.db.DbConnection;
import com.mycompany.cadastro_de_trecos.setup.AppSetup;
import java.sql.SQLException;
import java.util.Scanner;

public class Update extends AppSetup {
    
     public static void update() {

        // Inicializa e reserva recursos.
        int id = 0;
        String sql;

        // Cabeçalho da view.
        System.out.println(appName + "\n" + appSep);
        System.out.println("Editar registro");
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
            update();
        }

        try {

            // Obtém o registro solicitado do banco de dados.
            sql = "SELECT *, DATE_FORMAT(data, '%d/%m/%Y às %H:%i') AS databr FROM " + DBTABLE + " WHERE status = '2' and id = ?";
            conn = DbConnection.dbConnect();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            res = pstm.executeQuery();
            if (res.next()) {

                // Se tem registro, exibe na view.
               showRes(res);
               
                System.out.println("Insira os novos dados ou deixe em branco para manter os atuais:\n");

                // Instância de Scanner para obter dados do teclado.
                Scanner keyboard = new Scanner(System.in, "latin1");

                // Obtém o nome.
                /* Essa parte solicita ao usuário que digite o nome do item e armazena o valor na variável itemNome.
                   Em seguida, verifica se o valor digitado está vazio. 
                   Se estiver vazio, significa que o usuário não forneceu um novo valor para o nome e, 
                   portanto, atribui o valor existente (obtido da variável res ou do banco de dados) à variável itemNome.
                   Caso tenha digitado algo mantém os dados coletados no keyboard.nextline*/
            System.out.print("\tNome: ");
            String itemNome = keyboard.nextLine().trim();
            
            if (itemNome == ""){
            itemNome = res.getString("nome");
            }

            // Obtém a descrição. Usa a mesma lógica do itemNone.
            System.out.print("\tDescrição: ");
            String itemDescricao = keyboard.nextLine().trim();
            
             if (itemDescricao == ""){
            itemDescricao = res.getString("descricao");
            }
            //Obtém a localização. Usa a mesma lógica do itemNone.
            System.out.print("\tLocalização: ");
            String itemLocalizacao = keyboard.nextLine().trim();

            if (itemLocalizacao == ""){
            itemLocalizacao = res.getString("localizacao");
            }
            
            // Pede confirmação.
            /*Essa parte exibe uma mensagem para o usuário, solicitando a confirmação dos dados inseridos. 
              O usuário deve digitar "s" para confirmar ou qualquer outra entrada para cancelar a atualização. 
              A entrada do usuário é lida utilizando o objeto keyboard (um objeto Scanner).
              Se a entrada for igual a "s" (ignorando espaços em branco e com letras convertidas para minúsculas), 
              o código continua com a atualização do registro.
             */
            System.out.print("\nOs dados acima estão corretos? [s/N] ");
            if (keyboard.next().trim().toLowerCase().equals("s")) {

                /* Insere os dados na tabela usando PreparetedStatement. DBTABLE é a constante que armazena a tabela trecos
                   Essa parte é executada quando o usuário confirma que os dados estão corretos. 
                   Ela atualiza os valores dos campos "nome", "descricao" e "localizacao" na tabela do banco de dados.
                   O comando SQL utilizado é um UPDATE que utiliza placeholders (?) para os valores a serem atualizados.
                */
                sql = "UPDATE " + DBTABLE + " SET  nome = ?, descricao = ?, localizacao = ? WHERE id = ?";
                conn = DbConnection.dbConnect();
                /*O objeto pstm é um PreparedStatement que representa a consulta preparada. 
                  Os métodos setString() e setInt() são usados para definir os valores nos placeholders da consulta preparada,
                  utilizando os valores armazenados nas variáveis itemNome, itemDescricao, itemLocalizacao e id. */
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, itemNome);
                pstm.setString(2, itemDescricao);
                pstm.setString(3, itemLocalizacao);
                pstm.setInt(4, id);

                    if (pstm.executeUpdate() == 1) {

                        // Se o registro foi criado.
                        System.out.println("\nRegistro atualizado!");
                    } else {

                        // Falha ao criar registro.
                        System.out.println("Nada aconteceu!");
                    }

                } else {
                    System.out.println("\nNada aconteceu!");
                }

                // Fecha banco de dados.
                DbConnection.dbClose(res, stmt, pstm, conn);

                // Menu inferior da seção.
                System.out.println(appSep);
                System.out.println("Menu:\n\t[1] Menu principal\n\t[2] Editar outro\n\t[0] Sair");
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
                        update();
                        break;
                    default:
                        clearScreen();
                        System.out.println("Oooops! Opção inválida!\n");
                        update();
                }

            } else {
                // Se não tem registro.
                clearScreen();
                System.out.println("Oooops! Não achei nada!\n");
                update();
            }

        } catch (SQLException error) {

            // Tratamento de erros.
            System.out.println("Oooops! " + error.getMessage());
            System.exit(0);
        }

    }

}
