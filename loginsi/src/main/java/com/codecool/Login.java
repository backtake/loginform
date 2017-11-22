package com.codecool;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class Login implements HttpHandler {

    private JtwigModel model;
    private JtwigTemplate template;
    private Map inputs;
    private UserDao uDao = new UserDao();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();
        System.out.println(path);


        if (path.equals("/login")) {

            if (method.equals("GET")) {
                model = createModel("static/login/login.html");
            } else if (method.equals("POST")) {
                inputs = getInputs(httpExchange);

                try {
                    if (userExist(inputs)) {
                        model = createModel("static/login/welcome.html");
                        model.with("uname", inputs.get("uname"));
                    } else {
                        model = createModel("static/login/no_such_user.html");

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }



        response = template.render(model);
        System.out.println(response.getBytes().length);
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map getInputs(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        inputs = ParseForm.parseFormData(formData);

        return inputs;
    }

    private JtwigModel createModel(String path) {
        template = JtwigTemplate.classpathTemplate(path);
        model  = JtwigModel.newModel();

        return model;
    }

    private boolean userExist(Map inputs) throws SQLException {
        ArrayList<User> users = uDao.getUsers();
        for (User user: users) {
            if ((inputs.get("uname").equals(user.getLogin())) && (inputs.get("psw").equals(user.getPassword()))) {
                return true;
            }
        }
        return false;
    }
}
