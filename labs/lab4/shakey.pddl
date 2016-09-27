(define (domain shakey)
  (:requirements :adl :typing)
  (:types shakey, box, lightswitch, door, toy, room) 
  (:predicates
   (in ?t, ?r) ;; a toy is in a specific room
   (box_in ?b, ?r) ;; a box is in a room

   
   (belong ?l, ?r) ;; a lightswitch is bound to a room 
   (activated ?l) ;; is the lightswitch on? 


   (holding_l) ;; is the left hand currently holding something? 
   (holding_r) ;; what about the right hand? 
   (hold_l ?s - shakey, ?t-toy) ;; what is the left hand holding? 
   (hold_r ?s - shakey, ?t-toy) ;;

   (at ?s - shakey, ?r - room) ;; what room is shakey in
   
   (connecting ?d - door, ?r1 ?r2 - room) ;; a door connects two rooms
   (wide ?d - door) ;; a door is either wide or not

   )

;; shakey walking
(:action move
	 :parameters (?who - shakey ?from - room ?to - room ?door - door)
	 :precondition (and 
			(at ?who ?from) 
			(connecting ?door ?from ?to)
			)
	 :effect (and (not (at ?who ?from)) ;; he is now not where he were
		      (at ?who ?to))        ;; he is where he went
)

;; shakey activates a lightswitch in the room he is in
(:action switch_on
	 :parameters (?who - shakey, ?where - room, ?l - lightswitch, ?b - box)
	 :precondition(and
		       (at ?who ?where) ;; what room is shakey in? 
		       (belong ?l ?where) ;; the lightswitch needs to be in the same room? 
		       (not (activated ?l)) ;; light has to be off to be turned on
		       (box_in ?b ?where) ;; shakey needs a box in the same room to be able to reach the light switch
		       )
	 :effect ( activated ?l ) ;; light is now on
)

;; shakey turns off a lightswitch in the room he is in, practically the same as turning a lightswitch on
(:action switch_off
	 :parameters (?who - shakey, ?where - room, ?l - lightswitch, ?b - box)
	 :precondition(and
		       (at ?who ?where)
		       (belong ?l ?where)
		       (activated ?l)
		       (box_in ?b ?where) 
		       )
	 :effect ( not (activated ?l) )
)



;; shakey moves from a room with a box to a connected room, along with the box, given that he has a wide door to use
(:action push_box
	 :parameters (?who - shakey, ?from - room, ?to - room, ?b - box, ?door - door)
	 :precondition (and 
			(at ?who ?from) 
			(box_in ?b ?from)
			(connecting ?door ?from ?to)
			(wide ?door)
			)
	 :effect (and (not (at ?who ?from)) (at ?who ?to)
		      (not (box_in ?b ?from)) (box_in ?b ?to))
)

;; picks up an item with his left hand
(:action left_hand_pickup
	 :parameters (?who - shakey, ?what - toy, ?where - room, ?lit - lightswitch)
	 :precondition (and
			(at ?who ?where) ;; what room ar we operating in? 
			(in ?what ?where) ;; item has to be in same room
			(belong ?lit ?where) ;; the lightswitch has to be in this room 
			(activated ?lit) ;; the lightswitch has to be activated
			(not (holding_l)) ;; left hand has to be available
			)
	 :effect (and (not (in ?what ?where))
		      (hold_l ?who ?what)
		      (holding_l) ;; left hand is now busy 
		      )


)


;; see comments for left hand
(:action right_hand_pickup
	 :parameters (?who - shakey, ?what - toy, ?where - room, ?lit - lightswitch)
	 :precondition (and
			(at ?who ?where)
			(in ?what ?where)
			(belong ?lit ?where)
			(activated ?lit)
			(not (holding_r))
			)
	 :effect (and (not (in ?what ?where))
		      (hold_r ?who ?what)
		      (holding_r)
		      )


)

;; drops whatever is in our left hand
(:action left_hand_drop
	 :parameters (?who - shakey, ?what - toy, ?where - room)
	 :precondition (and
			(at ?who ?where) ;; what room are we operating in? 
			(hold_l ?who ?what) ;; the item to drop has to be in our left hand
			(holding_l) ;; the hand about to drop stuff has to be holding something
			;; note that shakey can drop stuff in a dark room despite only being able to pick up stuff in a lit room
			)
	 :effect (and 
		  (not (holding_l)) ;; free our hand
		  (not (hold_l ?who ?what)) ;; item is no longer in our hand
		  (in ?what ?where) ;; item is in the room we're standing in
		      )
)

;; see comments for left hand
(:action right_hand_drop
	 :parameters (?who - shakey, ?what - toy, ?where - room)
	 :precondition (and
			(at ?who ?where)
			(hold_r ?who ?what)
			(holding_r)
			)
	 :effect (and 
		  (not (holding_r)) 
		  (not (hold_r ?who ?what))
		  (in ?what ?where)
		      )
)




)
