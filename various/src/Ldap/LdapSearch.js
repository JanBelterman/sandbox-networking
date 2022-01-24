var ldap = require('ldapjs')

var client = ldap.createClient({
    url: 'ldap://ldap.forumsys.com'
})

client.bind('uid=tesla,dc=example,dc=com', 'password', function (err) {
    if (err) console.log(err)
})

var opts = {
    // filter: '(uid=tesla)',
    // attributes: ['mail']
}

client.search('uid=tesla,dc=example,dc=com', opts, function (err, res) {
    if (err) console.log(err)

    res.on('searchEntry', function (entry) {
        console.log('entry: ' + JSON.stringify(entry.object))
    })
    res.on('searchReference', function (referral) {
        console.log('referral: ' + referral.uris.join())
    })
    res.on('error', function (err) {
        console.error('error: ' + err.message)
    })
    res.on('end', function (result) {
        console.log('status: ' + result.status)
    })

})