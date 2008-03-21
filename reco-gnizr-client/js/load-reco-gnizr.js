var thisElement = document.getElementById("recognizrscript");
recognizrFrame = document.createElement("IFRAME");
recognizrFrame.src = "http://localhost:8080/reco-gnizr/html/reco-gnizr-frame.html?"
                      + document.location; // TODO: The whole url should not be sent

recognizrFrame.style.border = "none";
recognizrFrame.style.height = "200px";
recognizrFrame.style.width = "300px"; 
recognizrFrame.scrolling = "no";
thisElement.parentNode.appendChild(recognizrFrame);
