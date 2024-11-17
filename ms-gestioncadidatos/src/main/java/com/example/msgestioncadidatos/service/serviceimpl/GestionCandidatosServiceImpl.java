package com.example.msgestioncadidatos.service.serviceimpl;

import com.example.msgestioncadidatos.dto.PostulacionDto;
import com.example.msgestioncadidatos.entity.GestionCandidatos;
import com.example.msgestioncadidatos.feign.PostulacionFeign;
import com.example.msgestioncadidatos.repository.GestionCandidatosRepository;
import com.example.msgestioncadidatos.service.EmailService;
import com.example.msgestioncadidatos.service.GestionCandidatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class GestionCandidatosServiceImpl implements GestionCandidatosService {

    @Autowired
    private GestionCandidatosRepository gestionCandidatosRepository;

    @Autowired
    private PostulacionFeign postulacionFeign;

    @Autowired
    private EmailService emailService;

    private final Random random = new Random();

    @Override
    public List<GestionCandidatos> list() {
        List<GestionCandidatos> candidatosList = gestionCandidatosRepository.findAll();

        for (GestionCandidatos candidato : candidatosList) {
            if (candidato.getPostulacionId() != null) {
                Optional<PostulacionDto> postulacionDtoOptional = postulacionFeign.getById(candidato.getPostulacionId());
                postulacionDtoOptional.ifPresent(candidato::setPostulacionDto);
            }
        }
        return candidatosList;
    }

    @Override
    public Optional<GestionCandidatos> getById(Integer id) {
        return gestionCandidatosRepository.findById(id);
    }

    @Override
    public GestionCandidatos save(GestionCandidatos gestionCandidatos) {
        return gestionCandidatosRepository.save(gestionCandidatos);
    }

    @Override
    public GestionCandidatos update(GestionCandidatos gestionCandidatos) {
        return gestionCandidatosRepository.save(gestionCandidatos);
    }

    @Override
    public void delete(Integer id) {
        gestionCandidatosRepository.deleteById(id);
    }

    // Generar un código de verificación
    private String generateVerificationCode() {
        return String.valueOf(100000 + random.nextInt(900000)); // Código de 6 dígitos
    }

    // Enviar el código de verificación por correo
    @Override
    public void enviarCodigoVerificacion(String email) throws MessagingException {
        Optional<GestionCandidatos> optionalCandidato = gestionCandidatosRepository.findByCorreoElectronico(email);

        if (optionalCandidato.isPresent()) {
            GestionCandidatos candidato = optionalCandidato.get();
            String codigo = generateVerificationCode();
            candidato.setCodigoVerificacion(codigo);
            gestionCandidatosRepository.save(candidato);

            // Enviar correo al usuario con el código de verificación
            String asunto = "Código de Verificación para tu cuenta";
            String cuerpo = "Tu código de verificación es: " + codigo;
            emailService.sendEmail(email, asunto, cuerpo);
        } else {
            throw new RuntimeException("Candidato no encontrado con el correo proporcionado.");
        }
    }

    // Verificar el código ingresado por el usuario
    @Override
    public boolean verificarCodigo(String email, String codigoIngresado) throws MessagingException {
        Optional<GestionCandidatos> optionalCandidato = gestionCandidatosRepository.findByCorreoElectronico(email);

        if (optionalCandidato.isPresent()) {
            GestionCandidatos candidato = optionalCandidato.get();
            if (codigoIngresado.equals(candidato.getCodigoVerificacion())) {
                // Verificar el correo y limpiar el código
                candidato.setEmailVerificado(true);
                candidato.setCodigoVerificacion(null);
                gestionCandidatosRepository.save(candidato);

                // Enviar correo de confirmación de registro exitoso
                enviarCorreoConfirmacionRegistro(candidato);

                return true;
            }
        }
        return false;
    }

    private void enviarCorreoConfirmacionRegistro(GestionCandidatos candidato) throws MessagingException {
        String asunto = "Registro Completado en la Plataforma de Empleos";
        String cuerpo = String.format(
                "Estimado/a %s,\n\n" +
                        "¡Felicidades! Tu registro ha sido completado exitosamente en nuestra plataforma de búsqueda de empleos.\n" +
                        "A continuación, algunos detalles de tu perfil:\n\n" +
                        "Nombre Completo: %s\n" +
                        "Correo Electrónico: %s\n" +
                        "Teléfono: %s\n" +
                        "Carrera: %s\n" +
                        "Especialidad: %s\n\n" +
                        "Ahora puedes acceder a nuestra plataforma para explorar y postular a diversas ofertas laborales que se ajusten a tu perfil.\n" +
                        "Te deseamos mucho éxito en tu búsqueda de empleo.\n\n" +
                        "Atentamente,\nEl equipo de Plataforma de Empleos",
                candidato.getNombreCompleto(),
                candidato.getNombreCompleto(),
                candidato.getCorreoElectronico(),
                candidato.getTelefono(),
                candidato.getCarrera(),
                candidato.getEspecialidad()
        );

        emailService.sendEmail(candidato.getCorreoElectronico(), asunto, cuerpo);
    }



    @Override
    public void reenviarCodigoVerificacion(String email) throws MessagingException {
        // Buscar el candidato por correo electrónico
        Optional<GestionCandidatos> optionalCandidato = gestionCandidatosRepository.findByCorreoElectronico(email);

        if (optionalCandidato.isPresent()) {
            GestionCandidatos candidato = optionalCandidato.get();

            // Generar un nuevo código de verificación
            String nuevoCodigo = generateVerificationCode();
            candidato.setCodigoVerificacion(nuevoCodigo);
            candidato.setEmailVerificado(false); // Resetear el estado de verificación
            gestionCandidatosRepository.save(candidato);

            // Enviar el nuevo código por correo
            String asunto = "Nuevo Código de Verificación para tu cuenta";
            String cuerpo = "Tu nuevo código de verificación es: " + nuevoCodigo;
            emailService.sendEmail(email, asunto, cuerpo);
        } else {
            throw new IllegalArgumentException("Candidato no encontrado con el correo proporcionado: " + email);
        }
    }

}
