all:
	(cd sth-core; make $(MFLAGS) all)
	(cd sth-app; make $(MFLAGS) all)

clean:
	(cd sth-core; make $(MFLAGS) clean)
	(cd sth-app; make $(MFLAGS) clean)

install:
	(cd sth-core; make $(MFLAGS) install)
	(cd sth-app; make $(MFLAGS) install)
