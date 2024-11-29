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
import java.util.regex.Pattern;
import br.edu.fateczl.biblioteca2.R;
import br.edu.fateczl.biblioteca2.controller.AluguelController;
import br.edu.fateczl.biblioteca2.model.Aluguel;

public class AluguelFragment extends Fragment {
    private EditText etExemplarCodigo, etAlunoRA, etDataRetirada, etDataDevolucao;
    private Button btnInserir, btnBuscar, btnAtualizar, btnExcluir, btnListar;
    private TextView tvResultado;
    private AluguelController controller;
    private static final Pattern DATE_PATTERN =
            Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aluguel, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        etExemplarCodigo = view.findViewById(R.id.etExemplarCodigo);
        etAlunoRA = view.findViewById(R.id.etAlunoRA);
        etDataRetirada = view.findViewById(R.id.etDataRetirada);
        etDataDevolucao = view.findViewById(R.id.etDataDevolucao);
        tvResultado = view.findViewById(R.id.tvResultado);
        controller = new AluguelController(getContext());

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

    private boolean validarData(String data) {
        if (data == null || data.trim().isEmpty()) {
            return true;
        }
        if (!DATE_PATTERN.matcher(data).matches()) {
            return false;
        }
        try {
            String[] parts = data.split("/");
            int dia = Integer.parseInt(parts[0]);
            int mes = Integer.parseInt(parts[1]);
            int ano = Integer.parseInt(parts[2]);

            if (mes < 1 || mes > 12) return false;
            if (dia < 1 || dia > 31) return false;
            if (mes == 2 && dia > 29) return false;
            if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) return false;

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validarCampos() throws Exception {
        if (etExemplarCodigo.getText().toString().trim().isEmpty()) {
            throw new Exception("Código do exemplar é obrigatório");
        }
        if (etAlunoRA.getText().toString().trim().isEmpty()) {
            throw new Exception("RA do aluno é obrigatório");
        }
        if (etDataRetirada.getText().toString().trim().isEmpty()) {
            throw new Exception("Data de retirada é obrigatória");
        }

        String dataRetirada = etDataRetirada.getText().toString();
        String dataDevolucao = etDataDevolucao.getText().toString();

        if (!validarData(dataRetirada)) {
            throw new Exception("Data de retirada inválida. Use o formato dd/mm/aaaa");
        }
        if (!validarData(dataDevolucao)) {
            throw new Exception("Data de devolução inválida. Use o formato dd/mm/aaaa");
        }
    }

    private void inserir() {
        try {
            validarCampos();
            Aluguel aluguel = getAluguelFromInputs();
            controller.insert(aluguel);
            limparCampos();
            Toast.makeText(getContext(), "Aluguel inserido com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void buscar() {
        try {
            String exemplarCodigo = etExemplarCodigo.getText().toString().trim();
            String alunoRA = etAlunoRA.getText().toString().trim();
            String dataRetirada = etDataRetirada.getText().toString().trim();

            if (exemplarCodigo.isEmpty() || alunoRA.isEmpty() || dataRetirada.isEmpty()) {
                throw new Exception("Preencha código do exemplar, RA do aluno e data de retirada");
            }

            if (!validarData(dataRetirada)) {
                throw new Exception("Data de retirada inválida");
            }

            Aluguel aluguel = getAluguelFromInputs();
            aluguel = controller.findOne(aluguel);

            if (aluguel != null) {
                preencherCampos(aluguel);
            } else {
                Toast.makeText(getContext(), "Aluguel não encontrado", Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizar() {
        try {
            validarCampos();
            Aluguel aluguel = getAluguelFromInputs();
            controller.update(aluguel);
            limparCampos();
            Toast.makeText(getContext(), "Aluguel atualizado com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void excluir() {
        try {
            if (etExemplarCodigo.getText().toString().trim().isEmpty() ||
                    etAlunoRA.getText().toString().trim().isEmpty() ||
                    etDataRetirada.getText().toString().trim().isEmpty()) {
                throw new Exception("Preencha os campos para exclusão");
            }
            Aluguel aluguel = getAluguelFromInputs();
            controller.delete(aluguel);
            limparCampos();
            Toast.makeText(getContext(), "Aluguel excluído com sucesso",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void listar() {
        try {
            List<Aluguel> alugueis = controller.findAll();
            StringBuilder sb = new StringBuilder();
            for (Aluguel aluguel : alugueis) {
                sb.append(aluguel.toString()).append("\n\n");
            }
            tvResultado.setText(sb.toString());
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Aluguel getAluguelFromInputs() {
        Aluguel aluguel = new Aluguel();
        aluguel.setExemplarCodigo(
                Integer.parseInt(etExemplarCodigo.getText().toString().trim()));
        aluguel.setAlunoRA(
                Integer.parseInt(etAlunoRA.getText().toString().trim()));
        aluguel.setDataRetirada(etDataRetirada.getText().toString().trim());
        String dataDevolucao = etDataDevolucao.getText().toString().trim();
        aluguel.setDataDevolucao(dataDevolucao.isEmpty() ? null : dataDevolucao);
        return aluguel;
    }

    private void preencherCampos(Aluguel aluguel) {
        etExemplarCodigo.setText(String.valueOf(aluguel.getExemplarCodigo()));
        etAlunoRA.setText(String.valueOf(aluguel.getAlunoRA()));
        etDataRetirada.setText(aluguel.getDataRetirada());
        etDataDevolucao.setText(aluguel.getDataDevolucao() != null ?
                aluguel.getDataDevolucao() : "");
    }

    private void limparCampos() {
        etExemplarCodigo.setText("");
        etAlunoRA.setText("");
        etDataRetirada.setText("");
        etDataDevolucao.setText("");
        tvResultado.setText("");
    }
}