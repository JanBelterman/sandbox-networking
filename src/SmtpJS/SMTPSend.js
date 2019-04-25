const nodemailer = require("nodemailer")

async function main() {

    let transporter = nodemailer.createTransport({
        host: "smtp.gmail.com",
        port: 587,
        secure: false,
        auth: {
            user: 'xxx',
            pass: 'xxx'
        }
    })

    let i;
    // for (i = 0; i < 1000; i++) {
    let info = await transporter.sendMail({
        from: '"Jan Belterman" <jantestjan98@gmail.com>',
        to: "jantestjan98@gmail.com",
        subject: "Test from nodejs",
        text: "Hello world?",
        html: "<b>Hello world?</b>"
    })
    // .then((info) => {
    //     console.log(`Message sent: ${info.messageId}`)
    // }).catch(err => {
    //     console.error(err)
    // })
    console.log(`Message sent: ${info.messageId}`)

    // }

}

main().catch(console.error);