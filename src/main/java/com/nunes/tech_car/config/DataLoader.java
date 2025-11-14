package com.nunes.tech_car.config;

import com.nunes.tech_car.entity.Usuario;
import com.nunes.tech_car.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.DependsOn;
import com.nunes.tech_car.entity.Veiculo;
import com.nunes.tech_car.repository.VeiculoRepository;
import java.math.BigDecimal;
import java.util.Arrays;

@Component
@DependsOn("securityConfig")
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (usuarioRepository.findByEmail("admin@techcar.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setEmail("admin@techcar.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setNome("Administrador");
            admin.setPapeis(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
            usuarioRepository.save(admin);
            System.out.println("Usuario ADMIN criado: admin@techcar.com / admin123");
        }

        if (usuarioRepository.findByEmail("user@techcar.com").isEmpty()) {
            Usuario user = new Usuario();
            user.setEmail("user@techcar.com");
            user.setSenha(passwordEncoder.encode("user123"));
            user.setNome("Usuario Comum");
            user.setPapeis(Arrays.asList("ROLE_USER"));
            usuarioRepository.save(user);
            System.out.println("Usuario USER criado: user@techcar.com / user123");
        }

        if (veiculoRepository.count() == 0) {

            Veiculo veiculo1 = new Veiculo();
            veiculo1.setMarca("Toyota");
            veiculo1.setModelo("Corolla");
            veiculo1.setAno(2022);
            veiculo1.setPreco(new BigDecimal("85000.00"));
            veiculo1.setDescricao("Carro em excelente estado, unico dono");
            veiculo1.setImagemUrl("/images/car-placeholder.jpg");
            veiculoRepository.save(veiculo1);

            Veiculo veiculo2 = new Veiculo();
            veiculo2.setMarca("Honda");
            veiculo2.setModelo("Civic");
            veiculo2.setAno(2023);
            veiculo2.setPreco(new BigDecimal("95000.00"));
            veiculo2.setDescricao("Zero km, completo");
            veiculo2.setImagemUrl("/images/car-placeholder.jpg");
            veiculoRepository.save(veiculo2);

            Veiculo veiculo3 = new Veiculo();
            veiculo3.setMarca("Ford");
            veiculo3.setModelo("Ranger");
            veiculo3.setAno(2021);
            veiculo3.setPreco(new BigDecimal("120000.00"));
            veiculo3.setDescricao("Pickup em otimo estado");
            veiculo3.setImagemUrl("/images/car-placeholder.jpg");
            veiculoRepository.save(veiculo3);

            System.out.println("Veiculos de teste criados!");
        }
    }
}