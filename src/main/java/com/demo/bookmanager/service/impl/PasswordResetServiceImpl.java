//package com.demo.bookmanager.service.impl;
//
//import com.demo.bookmanager.entity.PasswordResetToken;
//import com.demo.bookmanager.entity.User;
//import com.demo.bookmanager.repository.PasswordResetTokenRepository;
//import com.demo.bookmanager.repository.UserRepository;
//import com.demo.bookmanager.service.PasswordResetService;
//import com.demo.bookmanager.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class PasswordResetServiceImpl implements PasswordResetService {
//    private final PasswordResetTokenRepository tokenRepository;
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private  JavaMailSender mailSender;
//
//    @Override
//    public void createPasswordResetToken (String email){
//        UserDetails userDetails = userService.loadUserByUsername(email);
//        String token = UUID.randomUUID().toString();
//        PasswordResetToken resetToken = new PasswordResetToken();
//        resetToken.setToken( token );
//        resetToken.setUser( (User) userDetails );
//        resetToken.setExpiryDate( LocalDateTime.now().plusHours( 1 ) );
//        tokenRepository.save( resetToken );
//
//        sendResetEmail( email, token );
//    }
//
//    @Override
//    public void resetPassword (String token, String newPassword) {
//        PasswordResetToken resetToken = tokenRepository.findByToken( token );
//        if (  resetToken == null ||  resetToken.getExpiryDate().isBefore( LocalDateTime.now() ) ) {
//            throw new IllegalArgumentException("Invalid or expired token");
//        }
//
//        User user = resetToken.getUser();
//        user.setPassword( passwordEncoder.encode( newPassword ) );
//        tokenRepository.delete(resetToken);
//        userRepository.save( user );
//    }
//
//    private void sendResetEmail (String email, String token) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo( email );
//        message.setSubject( "Password Reset Request" );
//        message.setText( "To reset your password, click the following link: "
//                + "http://localhost:8080/auth/reset-password?token=" + token );
//
//        mailSender.send( message );
//    }
//}
