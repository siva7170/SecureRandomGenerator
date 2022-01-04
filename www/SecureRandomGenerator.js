var exec = require('cordova/exec');

exports.generateSecureRandom = function (noOfLen, randomType, success, error) {
    exec(success, error, 'SecureRandomGenerator', 'generateSecureRandom', [noOfLen,randomType]);
};
