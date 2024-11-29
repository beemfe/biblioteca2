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
import br.edu.fateczl.biblioteca2.controller.LivroController;
import br.edu.fateczl.biblioteca2.model.Livro;

public class LivroFragment extends Fragment {
    private EditText etCodigo, etNome, etQtdPaginas, etISBN, etEdicao;
    private Button btnInserir, btnBuscar, btnAtualizar, btnExcluir, btnListar;
    private TextView tvResultado;
    private LivroController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_livro, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        etCodigo = view.findViewById(R.id.etCodigo);
        etNome = view.findViewById(R.id.etNome);
        etQtdPaginas = view.findViewById(R.id.etQtdPaginas);
        etISBN = view.findViewById(R.id.etISBN);
        etEdicao = view.findViewById(R.id.etEdicao);
        tvResultado = view.findViewById(R.id.tvResultado);
        controller = new LivroController(getContext());

        btnInserir = view.findViewById(R.id.btnInserir);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnAtualizar = view.findViewById(R.id.btnAtualizar);
        btnExcluir = view.findViewById(R.id.btnExcluir);
        btnListar = view.findViewById(R.id.btnListar);

        btnInserir.setOnClickListener(v -> inserir());
        btnBuscar.setOnClickListener(v -> buscar());
        btnAtualizar.setOnClickListener(v -> atualizar());
        btnExcluir.setOnClickListener(v -> excluir());
        btnListar.setOnClickListener(v -> listar());
    }

    private void inserir() {
        try {
            Livro livro = getLivroFromInputs();
            controller.insert(livro);
            limparCampos();
            Toast.makeText(getContext(), "Livro inserido com sucesso",
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
                Toast.makeText(getContext(), "Informe o código", Toast.LENGTH_SHORT).show();
                return;
            }
            Livro livro = getLivroFromInputs();
            livro = controller.findOne(livro);
            if (livro != null) {
                preencherCampos(livro);
            } else {
                Toast.makeText(getContext(), "Livro não encontrado", Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizar() {
        try {
            Livro livro = getLivroFromInputs();
            controller.update(livro);
            limparCampos();
            Toast.makeText(getContext(), "Livro atualizado com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        try {
            Livro livro = getLivroFromInputs();
            controller.delete(livro);
            limparCampos();
            Toast.makeText(getContext(), "Livro excluído com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void listar() {
        try {
            List<Livro> livros = controller.findAll();
            StringBuilder sb = new StringBuilder();
            for (Livro livro : livros) {
                sb.append(livro.toString()).append("\n\n");
            }
            tvResultado.setText(sb.toString());
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Livro getLivroFromInputs() throws Exception {
        Livro livro = new Livro();

        String codigoStr = etCodigo.getText().toString().trim();
        String qtdPaginasStr = etQtdPaginas.getText().toString().trim();
        String edicaoStr = etEdicao.getText().toString().trim();

        if (codigoStr.isEmpty()) {
            throw new Exception("Código é obrigatório");
        }

        try {
            livro.setCodigo(Integer.parseInt(codigoStr));

            if (!qtdPaginasStr.isEmpty()) {
                livro.setQtdPaginas(Integer.parseInt(qtdPaginasStr));
            }
            if (!edicaoStr.isEmpty()) {
                livro.setEdicao(Integer.parseInt(edicaoStr));
            }

            livro.setNome(etNome.getText().toString().trim());
            livro.setISBN(etISBN.getText().toString().trim());

            return livro;
        } catch (NumberFormatException e) {
            throw new Exception("Valor numérico inválido");
        }
    }

    private void preencherCampos(Livro livro) {
        etCodigo.setText(String.valueOf(livro.getCodigo()));
        etNome.setText(livro.getNome());
        etQtdPaginas.setText(String.valueOf(livro.getQtdPaginas()));
        etISBN.setText(livro.getISBN());
        etEdicao.setText(String.valueOf(livro.getEdicao()));
    }

    private void limparCampos() {
        etCodigo.setText("");
        etNome.setText("");
        etQtdPaginas.setText("");
        etISBN.setText("");
        etEdicao.setText("");
        tvResultado.setText("");
    }
}