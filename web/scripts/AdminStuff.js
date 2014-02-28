///////////////////////////////////////////
// KICK
///////////////////////////////////////////

function onKickError($data, $responseTarget) {
    setHoverText($responseTarget, "Konnte '"+ $data.playerName +"' nicht kicken. " + $data.reason);
}

function onKickSuccess($data, $responseTarget) {
    setHoverText($responseTarget, "'"+ $data.playerName +"' wurde gekickt!");
}

///////////////////////////////////////////
// BANN
///////////////////////////////////////////

function onBanSuccess($data, $responseTarget) {
    setHoverText($responseTarget, "'"+ $data.playerName +"' wurde gebannt! ( Grund: " + $data.reason + " )");
}

function onBanError($data, $responseTarget) {
    setHoverText($responseTarget, "Konnte '"+ $data.playerName +"' nicht bannen. " + $data.reason);
}