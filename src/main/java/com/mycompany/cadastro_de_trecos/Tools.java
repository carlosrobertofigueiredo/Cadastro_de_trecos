
package com.mycompany.cadastro_de_trecos;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tools {
   
    static String viewStatus;
    
    public static void showRes(ResultSet res) {
        try {
                if (res.getString("status").equals("1")) {
                        viewStatus = "BLOQUEADO";
                    } else {
                        viewStatus = "ATIVO";
                    }

                    // Exibe registro na view.
                    System.out.println(
                            "ID: " + res.getString("id") + "\n"
                            + "  Data de cadastro: " + res.getString("databr") + "\n"
                            + "  Nome: " + res.getString("nome") + "\n"
                            + "  Descrição: " + res.getString("descricao") + "\n"
                            + "  Localização: " + res.getString("localizacao") + "\n"
                            + "  Status: " + viewStatus + "\n"
                    );
        } catch (SQLException error) {

            // Tratamento de erros.
            System.out.println("Oooops! " + error.getMessage());
            System.exit(0);
        }

    }
    
    
}
