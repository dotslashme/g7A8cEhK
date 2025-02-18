# Val av tekniker och förklaringar

Jag har valt att använda mig av en statisk konfiguration för arbetstimmar. Orsaken till det är att det förmodligen är
ett värde som inte ändras speciellt ofta och därmed kan motiveras med en ny release av kod med en uppdaterad 
konfiguration. I en korrekt uppsatt cloud-miljö spelar det mindre roll, då en override av värdet kan konfigureras med
tex en miljövariabel i en deployment.

# Saker som inte gjorts klart

Metoden `getFreeSlots` ska utökas genom att använda sig av `addAll` i stället för `add` och metoden `createTimeSlot` ska
ta emot en `Duration` som motsvarar mötestiden som man vill boka. Mha den, ska metoden iterera över den tillgängliga 
tiden och skapa "timeslots" med en `Duration` som motsvarar den som efterfrågas och sedan returnera en lista i stället
för en singel timeslot som den gör i dag.