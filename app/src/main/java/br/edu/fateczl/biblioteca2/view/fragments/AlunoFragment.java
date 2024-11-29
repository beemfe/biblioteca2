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
import br.edu.fateczl.biblioteca2.controller.AlunoController;
import br.edu.fateczl.biblioteca2.model.Aluno;

public class AlunoFragment extends Fragment {
    private EditText etRA, etNome, etEmail;
    private Button btnInserir, btnBuscar, btnAtualizar, btnExcluir, btnListar;
    private TextView tvResultado;
    private AlunoController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aluno, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        etRA = view.findViewById(R.id.etRA);
        etNome = view.findViewById(R.id.etNome);
        etEmail = view.findViewById(R.id.etEmail);
        tvResultado = view.findViewById(R.id.tvResultado);
        controller = new AlunoController(getContext());

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
            validarCampos();
            Aluno aluno = getAlunoFromInputs();
            controller.insert(aluno);
            limparCampos();
            Toast.makeText(getContext(), "Aluno inserido com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void buscar() {
        try {
            if (etRA.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Informe o RA",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            Aluno aluno = getAlunoFromInputs();
            aluno = controller.findOne(aluno);
            if (aluno != null) {
                preencherCampos(aluno);
            } else {
                Toast.makeText(getContext(), "Aluno não encontrado",
                        Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizar() {
        try {
            validarCampos();
            Aluno aluno = getAlunoFromInputs();
            controller.update(aluno);
            limparCampos();
            Toast.makeText(getContext(), "Aluno atualizado com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        try {
            Aluno aluno = getAlunoFromInputs();
            controller.delete(aluno);
            limparCampos();
            Toast.makeText(getContext(), "Aluno excluído com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void listar() {
        try {
            List<Aluno> alunos = controller.findAll();
            StringBuilder sb = new StringBuilder();
            for (Aluno aluno : alunos) {
                sb.append(aluno.toString()).append("\n\n");
            }
            tvResultado.setText(sb.toString());
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void validarCampos() throws Exception {
        String ra = etRA.getText().toString().trim();
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (ra.isEmpty()) {
            throw new Exception("RA é obrigatório");
        }
        if (nome.isEmpty()) {
            throw new Exception("Nome é obrigatório");
        }
        if (email.isEmpty()) {
            throw new Exception("Email é obrigatório");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new Exception("Email inválido");
        }
    }

    private Aluno getAlunoFromInputs() throws Exception {
        Aluno aluno = new Aluno();
        try {
            String raStr = etRA.getText().toString().trim();
            if (!raStr.isEmpty()) {
                aluno.setRA(Integer.parseInt(raStr));
            }
            aluno.setNome(etNome.getText().toString().trim());
            aluno.setEmail(etEmail.getText().toString().trim());
            return aluno;
        } catch (NumberFormatException e) {
            throw new Exception("RA inválido");
        }
    }

    private void preencherCampos(Aluno aluno) {
        etRA.setText(String.valueOf(aluno.getRA()));
        etNome.setText(aluno.getNome());
        etEmail.setText(aluno.getEmail());
    }

    private void limparCampos() {
        etRA.setText("");
        etNome.setText("");
        etEmail.setText("");
        tvResultado.setText("");
    }
}