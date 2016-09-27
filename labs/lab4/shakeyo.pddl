;; Shakey's World

;; Shakey's world, or versions thereof, is an AI classic. A robot, named Shakey, is moving around in a set of rooms, connected by doors. In some of the rooms there are light switches, so that the light in the room can be on or off. Spread throughout this world there are a number of big boxes and a number of smaller things (balls, blocks with letters on them, toy tractors and whatnot).
;; Here's an example of a rather small world layout:


 ;; -------------------------------------------------------------------------
 ;; |                       |                       |                       |
 ;; |                       |                       |                       |
 ;; |       light switch 1 -|- light switch2        |- light switch3        |
 ;; |                       |                       |                       |
 ;; |       ---             |                     door2                     |
 ;; |       | |           door1      shakey         |                       |
 ;; |       ---           (wide)                    |                       |
 ;; |       box             |                       |                       |
 ;; |                       |                     door3                     |
 ;; |                       |                     (wide)                    |
 ;; |        room1          |        room2          |         room3         |
 ;; -------------------------------------------------------------------------

;; The following restrictions apply to Shakey's actions:

;; Shakey can carry small objects, but only two at a time because he has only two grippers.

;; For Shakey to be able to find a small object in a room, the light must be on in that room.

;; Shakey can not carry boxes, but can push them around. Boxes can only be pushed through wide doors, not narrow ones.

;; To switch the light in a room on or off Shakey must climb onto a box that is positioned below the switch (otherwise he can not reach the switch). This may in turn require moving the box into position, possibly from another room.

;; Typical problems for Shakey may be to find a specific object (e.g. the green block labeled "G") and bring it to a specific place, to turn the lights on/off in every room, or to remove all boxes and objects from one of the rooms (the last problem may require ADL, since the goal is a "for all").

(define (domain shakeysworld)
  (:requirements :adl :typing)

  (:types shakey hand box lightswitch door toy room )

  (:predicates
    (adj ?r1 ?r2 - room) ; rooms are next to each other
    (tin ?toy - toy ?r - room) ; toys in rooms
    (tinh ?toy - toy ?h - hand) ; toy in hand
    (bin ?box - box ?r - room) ; boxes are in rooms as well
    (sin ?s - shakey ?r - room) ; shakey is in a room

    (lin ?lsw - lightswitch ?r - room) ; light switch in room
    (lon ?lsw - lightswitch ?r - room) ; light switch on/off

    (holdl ?s - shakey ?t - toy) ; shakey has a left hand for toys
    (holdr ?s - shakey ?t - toy) ; and a right hand as well
    (busy1h ?left - hand) ; one hand busy
    (busy2h ?right - hand) ; both hands busy
    (swhand ?h1 ?h2 - hand) ; swap hands

    (Doorway ?d - door ?r1 ?r2 - room) ; a door between two rooms
    (iswide ?d - door) ; wide doorway
  )

  (:action move
    :parameters (?who - shakey
                 ?from - room
                 ?to - room
                 ?door - door)
    :precondition (and
                    (sin ?who ?from)
                    (Doorway ?door ?from ?to)
                  )
    :effect (and
                (not (sin ?who ?from))
                (sin ?who ?to)
      )
  )

  (:action push
    :parameters (?box - box
                 ?who - shakey
                 ?from - room
                 ?to - room
                 ?door - door
                )
    :precondition (and
                    (sin ?who ?from)
                    (Doorway ?door ?from ?to)
                    (iswide ?door)
                    (bin ?box ?from)
                  )
    :effect (and
                (not (sin ?who ?from))
                (sin ?who ?to)
                (not (bin ?box ?from))
                (bin ?box ?to)
            )
  )

  (:action flipon
    :parameters (?l - lightswitch
                 ?who - shakey
                 ?where - room
                 ?box - box
    )
    :precondition (and
                    (sin ?who ?where)
                    (lin ?l ?where)
                    (bin ?box ?where)
                  )
    :effect (and
              (not (lon ?l ?where))
    )
  )

  (:action flipoff
    :parameters (?l - lightswitch
                 ?who - shakey
                 ?where - room
                 ?box - box
    )
    :precondition (and
                    (sin ?who ?where)
                    (lin ?l ?where)
                    (bin ?box ?where)
                  )
    :effect (and
              (lon ?l ?where)
    )
  )

  (:action pickup
    :parameters (
      (?toy - toy)
      (?who - shakey)
      (?where - room)
      (?h1 - hand)
      (?h2 - hand)
      )
    :precondition (and
                    (sin ?who ?where)
                    (tin ?toy ?where)
                    (not (or (busy1h ?h1) (busy2h ?rh))) ; meh, rethink
                  )
    :effect (and
              (not (tin ?toy ?where))
              (tinh ?)
            )
    )


)
