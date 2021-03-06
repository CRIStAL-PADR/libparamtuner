/*
 *  libParamTuner
 *  Copyright (C) 2017 Marc Baloup, Veïs Oudjail
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
#include "FileSystemWatcher.hpp"


#if defined __linux__
	#include "linux/InotifyFileSystemWatcher.hpp"
#elif defined __APPLE__
	#include "osx/FSEventsFileSystemWatcher.hpp"
#elif defined _WIN32
	#include "windows/Win32FileSystemWatcher.hpp"
#endif

#include <string>

using namespace std;


/*
 * FileSystemWatcher implementation
 */
FileSystemWatcher::FileSystemWatcher(const string &path, void (*callback)(void)) :
			path(path),
			callback(callback) {
}
void FileSystemWatcher::receiveSignal() {
	update();
	callback();
}
string FileSystemWatcher::getPath() const {
	return path;
}

/* static method */
FileSystemWatcher* FileSystemWatcher::createFileSystemWatcher(const std::string &path, void (*callback)(void)) {
	
	#if defined __linux__
		return new InotifyFileSystemWatcher(path, callback);
	#elif defined __APPLE__
		return new FSEventsFileSystemWatcher(path, callback);
	#elif defined _WIN32
		return new Win32FileSystemWatcher(path, callback);
	#else
		#error "operating system not supported"
	#endif
	
}

