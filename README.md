## Compile and transpile client (-w is waiting for changes)
mill -w client.fastLinkJS

## Compile and run server
mill -w server.runBackground (see http://localhost:8001/)

## Configure intellij support
mill mill.bsp.BSP/install

## Adding a specie to the sim
### Server :
- Create new class in core.domain.species,
- Add wanted traits
- Implement said traits (and any behavior in update method)

### Js :
- Create a new drawer in drawers.species
- Add mapping to your drawer in GenericEntityDrawer!

### Gif of the simulation
[ecosystem_12_06_2.gif](ecosystem_12_06_2.gif)