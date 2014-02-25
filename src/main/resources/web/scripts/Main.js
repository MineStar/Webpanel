            $(document).ready(function() { 
                            
                ////////////////////////////////////////////////////
                //
                // GLOBAL-VARS
                //
                //////////////////////////////////////////////////// 

                var HTTPServer = "http://localhost:8000/";
                var $responseDIV = $('[type="ajaxResponse"]');                  
                var requestPossible = 1;
            
                ////////////////////////////////////////////////////
                //
                // INTITALIZE GUI
                //
                ////////////////////////////////////////////////////                 
                   
                $responseDIV.hide();   
                $("#accordion").accordion();
                $(".button").button();
                           
                ////////////////////////////////////////////////////
                //
                // RESPONSE
                //
                ////////////////////////////////////////////////////
                
                $('[type="ajaxResponse"]').click(function() {
                    if($(this).attr("class") == "ui-state-error ui-corner-all" || $(this).attr("class") == "ui-state-highlight ui-corner-all") {
                        $(this).hide();
                    }
                });
                
                function showResponseDIV(className, message) {
                    $responseDIV.attr('class', className);                            
                    $responseDIV.html(message);
                    $responseDIV.show();
                }
           
                ////////////////////////////////////////////////////
                //
                // ELEMENT-FUNCTIONS
                //
                ////////////////////////////////////////////////////
                
                function callFunction(functionName, data) {
                    var fn = window[functionName];                                    
                    if(typeof fn === "function") {
                        fn.apply(window, data);
                    }                    
                }
           
                ////////////////////////////////////////////////////
                //
                // ELEMENT-FUNCTIONS
                //
                ////////////////////////////////////////////////////
                
                function enableAllElementsOfType(identifier) {
                    $(identifier).removeAttr("disabled");
                    $(identifier).attr("style", "opacity: 1;");
                }
                
                function disableAllElementsOfType(identifier) {
                    $(identifier).attr("disabled", "disabled");
                    $(identifier).attr("style", "opacity: 0.5;");
                }
                
                ////////////////////////////////////////////////////
                //
                // POST-REQUEST
                //
                ////////////////////////////////////////////////////
                
                // find every HTML-Element where "type" is "postRequest"     
                $('[type="postRequest"]').each(function() {
                    // add the onClick-event
                    $(this).click(function(event){
                        // if we already have a request, return here...
                        if(requestPossible !== 1) {
                            return;
                        }
                    
                        // get the target-form-name
                        var target = $(this).attr("target");                        
                        
                        // get the corresponding form-element
                        var $value = '[name="' +target+ '"]';        

                        // get the target-URL
                        var targetURL = HTTPServer + $($value).attr("url");
                        
                        // get the message
                        var showResponse = $($value).attr("showResponse");
                        showResponse = (showResponse == null || showResponse == "true");
                        
                        var message = $($value).attr("message");
                        var onSuccessMethod = $($value).attr("onSuccess");                        
                        var onErrorMethod = $($value).attr("onError");
                        
                        // get the responsefield
                        $responseField = $('[type="ajaxResponse"]');
                        
                        // create json
                        var inputs = {};
                        
                        // iterate over every inputfield in the form
                        $($value + " :input").each(function() {    
                            // add each elements name and value
                            inputs[$(this).attr('name')] = $(this).val(); 
                        });
                        
                        // construct JSON
                        var jsonData = JSON.stringify(inputs);
                        
                        // update style
                        requestPossible = 0;
                        
                        $($responseDIV).hide();
                        if(showResponse) {
                            showResponseDIV("ui-state-highlight ui-corner-all", "<b>Info</b>:<br>" + message + "<br><br><b>Payload:</b><br>" + jsonData.toString());
                        }
                        
                        // disable buttons
                        disableAllElementsOfType(".button");
                        
                        // send a POST-Request with ajax                        
                        $.ajax({
                            type: "POST",
                            url: targetURL,   
                            data: jsonData,
                            success: function (data, textData, jqXHR) {              
                                // by default: we show an error
                                if(showResponse) {
                                    showResponseDIV("ui-state-error ui-corner-all", "<b>Fehler:</b><br> Die Antwort konnte nicht verarbeitet werden!");      
                                }
                                
                                // parse the JSON
                                var answerJSON = jQuery.parseJSON(textData);
                                if(answerJSON.status == 200) {                                    
                                    // handle success
                                    if(showResponse) {
                                        showResponseDIV("ui-state-highlight ui-corner-all", "<b>Info:</b><br>" + answerJSON.message);
                                    }
                                    if(onSuccessMethod != null) {
                                        callFunction(onSuccessMethod, answerJson);
                                    }
                                } else {
                                    // handle error                                    
                                    if(showResponse) {
                                        showResponseDIV("ui-state-error ui-corner-all", "<b>Fehler:</b><br>" + answerJSON.message);
                                    }
                                    if(onErrorMethod != null) {
                                        callFunction(onErrorMethod, answerJson);
                                    }
                                }
                                
                                enableAllElementsOfType(".button");
                                requestPossible = 1;
                            },                             
                            error: function (jqXHR, textStatus, errorThrown) {  
                                if(showResponse) {
                                    showResponseDIV("ui-state-error ui-corner-all", "<b>Fehler:</b><br>Möglicherweise ist das Skript nicht erreichbar.");                                
                                }
                                if(onErrorMethod != null) {
                                    callFunction(onErrorMethod, null);
                                }
                                enableAllElementsOfType(".button");
                                requestPossible = 1;
                            }
                        });                    
                    }); 
                });
            });