
# Spring-Estacionamento
## Projeto usado somente para aprendizado na framework Spring.
###### Uma API simples que cadastra, busca, atualiza e deleta dados sobre estacionamento de condominios.

Essa API funciona através de uma única URL
> http://localhost:8080/parking-spot

Essa URL vai ser utilizada em todos os contextos, mudando somente o método de uso.
Antes de utilizar a API, certifique-se de configurar o seu banco de dados SQL ![aqui] https://github.com/YuriFernandes150/Spring-Estacionamento/blob/master/src/main/resources/application.properties

### POST
Para cadastrar um novo veículo, utiliza-se a URL em um método POST, enviando o seguinte JSON.
```
{
    "parkingSpotNumber": "0002",
    "licensePlateNumber": "BDE8000",
    "brandCar": "WW",
    "modelCar": "Gol",
    "colorCar": "Azul",
    "responsibleName": "Moises",
    "apartment": "802",
    "block": "B"
}

```
