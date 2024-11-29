/*
 *@author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.biblioteca2.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import java.util.List;
import br.edu.fateczl.biblioteca2.R;
import br.edu.fateczl.biblioteca2.controller.RevistaController;
import br.edu.fateczl.biblioteca2.model.Revista;

public class RevistaFragment extends Fragment {
    private EditText etCodigo, etNome, etQtdPaginas, etISSN;
    private Button btnInserir, btnBuscar, btnAtualizar, btnExcluir, btnListar;
    private TextView tvResultado;
    private RevistaController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revista, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        etCodigo = view.findViewById(R.id.etCodigo);
        etNome = view.findViewById(R.id.etNome);
        etQtdPaginas = view.findViewById(R.id.etQtdPaginas);
        etISSN = view.findViewById(R.id.etISSN);
        tvResultado = view.findViewById(R.id.tvResultado);
        controller = new RevistaController(getContext());

        btnInserir = view.findViewById(R.id.btnInserir);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnAtualizar = view.findViewById(R.id.btnAtualizar);
        btnExcluir = view.findViewById(R.id.btnExcluir);
        btnListar = view.findViewById(R.id.btnListar);

        setupButtonListeners();
    }

    private void setupButtonListeners() {
        btnInserir.setOnClickListener(v -> inserir());
        btnBuscar.setOnClickListener(v -> buscar());
        btnAtualizar.setOnClickListener(v -> atualizar());
        btnExcluir.setOnClickListener(v -> excluir());
        btnListar.setOnClickListener(v -> listar());
    }

    private void inserir() {
        try {
            validarCamposObrigatorios();
            Revista revista = getRevistaFromInputs();
            controller.insert(revista);
            limparCampos();
            Toast.makeText(getContext(), "Revista inserida com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void buscar() {
        try {
            String codigo = etCodigo.getText().toString().trim();
            if (codigo.isEmpty()) {
                Toast.makeText(getContext(), "Informe o código",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Revista revista = new Revista();
            revista.setCodigo(Integer.parseInt(codigo));
            revista = controller.findOne(revista);
            if (revista != null) {
                preencherCampos(revista);
            } else {
                Toast.makeText(getContext(), "Revista não encontrada",
                        Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Código inválido",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void validarCamposObrigatorios() throws Exception {
        String codigo = etCodigo.getText().toString().trim();
        String nome = etNome.getText().toString().trim();
        String qtdPaginas = etQtdPaginas.getText().toString().trim();
        String issn = etISSN.getText().toString().trim();

        if (codigo.isEmpty() || nome.isEmpty() || qtdPaginas.isEmpty() || issn.isEmpty()) {
            throw new Exception("Todos os campos são obrigatórios");
        }

        if (!issn.matches("^\\d{8}$")) {
            throw new Exception("ISSN deve conter 8 dígitos");
        }

        try {
            int paginas = Integer.parseInt(qtdPaginas);
            if (paginas <= 0) {
                throw new Exception("Quantidade de páginas deve ser maior que zero");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Quantidade de páginas inválida");
        }
    }

    private void atualizar() {
        try {
            Revista revista = getRevistaFromInputs();
            controller.update(revista);
            limparCampos();
            Toast.makeText(getContext(), "Revista atualizada com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        try {
            Revista revista = getRevistaFromInputs();
            controller.delete(revista);
            limparCampos();
            Toast.makeText(getContext(), "Revista excluída com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void listar() {
        try {
            List<Revista> revistas = controller.findAll();
            StringBuilder sb = new StringBuilder();
            for (Revista revista : revistas) {
                sb.append(revista.toString()).append("\n\n");
            }
            tvResultado.setText(sb.toString());
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Revista getRevistaFromInputs() throws Exception {
        Revista revista = new Revista();
        try {
            revista.setCodigo(Integer.parseInt(etCodigo.getText().toString().trim()));
            revista.setNome(etNome.getText().toString().trim());
            revista.setQtdPaginas(Integer.parseInt(etQtdPaginas.getText().toString().trim()));
            revista.setISSN(etISSN.getText().toString().trim());
        } catch (NumberFormatException e) {
            throw new Exception("Valor numérico inválido");
        }
        return revista;
    }

    private void preencherCampos(Revista revista) {
        etCodigo.setText(String.valueOf(revista.getCodigo()));
        etNome.setText(revista.getNome());
        etQtdPaginas.setText(String.valueOf(revista.getQtdPaginas()));
        etISSN.setText(revista.getISSN());
        tvResultado.setText("");
    }

    private void limparCampos() {
        etCodigo.setText("");
        etNome.setText("");
        etQtdPaginas.setText("");
        etISSN.setText("");
        tvResultado.setText("");
    }
}