/**
 * Created by marco on 28/02/16.
 */
/**
 * CALCOLO CODICE FISCALE PERSONE FISICHE
 * secondo il DECRETO MINISTERIALE 23 dicembre 1976.
 * "Sistemi di codificazione dei soggetti da iscrivere all'anagrafe tributaria"
 */

/*
 Check if character is in alphabet
 */
function isLetter(char) {
    return char.length === 1 && char.match(/[a-z]/i);
}
/*
 Check if the character is a vowel in Italian alphabet
 */
function isVowel(char){
    var vowelsIta = "AEIOU";
    var id = vowelsIta.indexOf(char.toUpperCase());
    if(id == -1){
        return false;
    } else {
        return true;
    }
}
/*
 Algoritmo per estrarre 3 lettere dal cognome
 */
function processLastname(lastname){
    // resulting string
    var str = '';
    var vowels = '';
    // remove spaces
    lastname.trim();
    // cycle over the string looking for consonant
    for(var i=0; i<lastname.length;i++){
        // only consonant
        var char = lastname.charAt(i).toUpperCase();
        // check it is a letter
        if(isLetter(char)) {
            if (!isVowel(char)) {
                str += char;
            } else {
                vowels += char;
            }
            // if 3 consonants found
            if (str.length == 3) {
                break;
            }
        }
    }
    // cycle over vowels
    if(str.length < 3){
        for(var i=0; i<vowels.length;i++){
            // insert vowels
            var char = vowels.charAt(i);
            str = str + char;
            // if 3 chars found
            if(str.length == 3){
                break;
            }
        }
    }
    // if necessary insert X char
    if(str.length < 3){
        for(var i=str.length-1; i<3;i++){
            // insert X char
            str = str + 'X';
        }
    }
    return str;
}
/*
 Algoritmo per estrarre 3 lettere dal nome
 */
function processFirstname(firstname){
    // resulting string
    var str = '';
    var vowels = '';
    var consonants = '';
    // remove spaces
    firstname.trim();
    // cycle over the string looking for consonants/vowels
    for(var i=0; i<firstname.length;i++){
        // only consonant
        var char = firstname.charAt(i).toUpperCase();
        // check it is a letter
        if (isLetter(char)) {
            if (isVowel(char)) {
                vowels += char;
            } else {
                consonants += char;
            }
        }
    }
    // if more then 3 consonants
    if(consonants.length > 3){
        str = consonants.charAt(0) + consonants.slice(2,4);
    } else {
        // cycle on consonants
        for(var i=0; i<consonants.length; i++){
            str += consonants.charAt(i);
            if(str.length == 3){
                break;
            }
        }
        // cycle on vowels
        if(str.length < 3){
            for(var i=0; i<vowels.length; i++){
                str += vowels.charAt(i);
                if(str.length == 3){
                    break;
                }
            }
        }
        // if needed, complete with X
        if(str.length < 3){
            for(var i=str.length-1; i<3; i++){
                str += 'X';
            }
        }
    }
    return str;
}
/*
 Get 5 chars from date
 */
function processDateOfBirth(dob, sex){
    // resulting string
    var str = '';
    dob = new Date(dob);
    // year
    var year = dob.getFullYear().toString();
    // day
    var dd = dob.getDate();
    if(sex == 'F'){
        dd += 40;
    }
    dd = dd.toString();
    if(dd.length == 1){
        dd = '0' + dd;
    }
    // month
    var convTable = 'ABCDEHLMPRST';
    var mm = convTable.charAt(dob.getMonth());

    str = year.slice(2) + mm + dd;
    return str;
}
/*
 Check character
 */
function checkCharacter(tmpCode){
    var numbers = '0123456789';
    var letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var oddCodeNumbers = [1,0,5,7,9,13,15,17,19,21];
    var oddCodeLetters = [1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23];
    var tmpChar;
    var sum = 0;
    // cycle over the input string
    for(var i=0;i<tmpCode.length;i++){
        tmpChar = tmpCode.charAt(i).toUpperCase();
        // even case
        if((i+1)%2 == 0){
            // letter
            if(isLetter(tmpChar)){
                sum += letters.indexOf(tmpChar);
            } else {
                // integer
                sum += parseInt(tmpChar);
            }
        } else {
            // odd
            // letter
            if(isLetter(tmpChar)){
                sum += oddCodeLetters[letters.indexOf(tmpChar)];
            } else {
                // integer
                sum += oddCodeNumbers[numbers.indexOf(tmpChar)];
            }
        }
    }
    return letters.charAt(sum%26);
}
/*
 Funzione per il calcolo del codice fiscale
 */
function calcolaCodiceFiscale(firstname, lastname, dob, pobCode, sex){
    // compose the first 15 chars
    var str = processLastname(lastname) + processFirstname(firstname) + processDateOfBirth(dob, sex) +
        pobCode;
    // adding the final char
    return str + checkCharacter(str);
}
