import React from 'react';
import { FaTimes } from 'react-icons/fa';

interface SidebarProps {
  isSidebarOpen: boolean;
  closeSidebar: () => void;
}

const Sidebar: React.FC<SidebarProps> = ({ isSidebarOpen, closeSidebar }) => {

  return (
    <div className={`sidebar ${isSidebarOpen ? 'active' : ''}`}>
      <button className="close-button" onClick={closeSidebar}>
        <FaTimes size={32} color="black" />
      </button>

      <a href="#about-us" className="font-dela" onClick={closeSidebar}>
      </a>
      <a href="#products" className="font-dela" onClick={closeSidebar}>
      </a>
      <a href="#contacts" className="font-dela" onClick={closeSidebar}>
      </a>

      <a
        href="https://lk.sibnn.ai/"
        className="font-dela"
        target="_blank"
        onClick={closeSidebar}
        rel="noreferrer"
      >
      </a>
    </div>
  );
};

export default Sidebar;
