package br.com.cps.forum.until

import br.com.cps.forum.dto.MailForm
import br.com.cps.forum.model.User

fun builderMailTopico(listEmail: List<String>, model: User): MailForm {
    return MailForm(
        email = listEmail,
        subject = "Confirmação de Nova Postagem no CPS Fórum",
        body = """
        Olá ${model.firstName} ${model.lastName},
        
        Parabéns! Estamos felizes em informar que você acabou de criar um novo tópico no nosso fórum, o CPS Fórum. É ótimo ver você compartilhando seus conhecimentos e contribuindo para a nossa comunidade.
        
        Seu tópico está agora disponível para visualização por outros membros da comunidade. Encorajamos você a participar das discussões e interagir com outros usuários que possam estar interessados no assunto que você abordou.
        
        Para visualizar seu tópico e acompanhar as respostas dos outros membros, clique no link abaixo:
        
        [Link para o tópico]
        
        Agradecemos por sua participação e esperamos ver mais contribuições suas no futuro.
        
        Atenciosamente,
        CPS Fórum
        """
    )
}